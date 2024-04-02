package com.digital.challenge.services.impl;

import com.digital.challenge.dto.NewsDto;
import com.digital.challenge.entities.News;
import com.digital.challenge.exception.NewsException;
import com.digital.challenge.repositories.NewsRepository;
import com.digital.challenge.services.NewsService;
import com.digital.challenge.utils.ParseHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public void save(NewsDto[] newsDto) {
        try {
            List<News> news = Arrays.stream(newsDto)
                    .map(n -> modelMapper.map(n, News.class))
                    .collect(Collectors.toList());
            news.forEach( n -> newsRepository.findById(n.getStoryId())
                    .orElseGet(()-> newsRepository.save(n))
            );
        } catch (NewsException e) {
            log.error("[NewsService] Error saving news", e);
        }
    }


    @Override
    public List<NewsDto> getAll(Pageable pageable, String author, String storyTitle) {
        List<NewsDto> newsDtos;
        try {
            final Specification<News> specification = ParseHelper.filterNews(author, storyTitle);
            Page<News> news = newsRepository.findAll(specification, pageable);
            newsDtos = news.stream()
                    .filter(v->v.getVisible())
                    .map(n -> modelMapper.map(n, NewsDto.class))
                    .collect(Collectors.toList());
        } catch (NewsException e) {
            log.error("Error get all news ", e);
            throw e;
        }
        return newsDtos;
    }

    @Override
    public boolean delete(Long id) {
        try {
            newsRepository.findById(id).orElseThrow(
                    NewsException::new
            );
            newsRepository.updateVisibiltyById(id);
            log.info("Deactivate news Sucessful Item {}", id);
            return true;
        }catch (NewsException e){
            log.error("Error to delete news ", e);
            throw e;
        }
    }


}
