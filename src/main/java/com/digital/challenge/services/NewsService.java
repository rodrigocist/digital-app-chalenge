package com.digital.challenge.services;

import com.digital.challenge.dto.NewsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {

    void save(NewsDto[] newsDto);

    List<NewsDto> getAll(Pageable pageable, String author, String storyTitle);

    boolean delete(Long id);


}
