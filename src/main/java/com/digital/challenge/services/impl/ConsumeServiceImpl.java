package com.digital.challenge.services.impl;

import com.digital.challenge.dto.NewsDto;
import com.digital.challenge.client.Consume;
import com.digital.challenge.exception.NewsException;
import com.digital.challenge.services.ConsumeService;
import com.digital.challenge.services.NewsService;
import com.digital.challenge.utils.MapperHelper;
import com.digital.challenge.utils.ParseHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class ConsumeServiceImpl implements ConsumeService {

    @Value("${news.api:https://hn.algolia.com/api/v1/search_by_date?query=java}")
    private String newsUrl;

    @Autowired
    private Consume consume;

    @Autowired
    private NewsService newsService;

    @Override
    public void consumeApi(){
        try {
            log.info("[ConsumeService] Get news from external API {}" , newsUrl);
            String news = consume.consume(newsUrl);
            Optional<String> items = ParseHelper.findItems(news);
            if(items.isPresent()){
                NewsDto[] newsDto = MapperHelper.mapperNews(items.get());
                newsService.save(newsDto);
            }
        } catch (NewsException | JsonProcessingException e) {
            log.error("Error to get news from api", e);
        }
    }

}
