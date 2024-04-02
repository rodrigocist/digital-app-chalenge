package com.digital.challenge.utils;

import com.digital.challenge.dto.NewsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperHelper {

    public static NewsDto[] mapperNews(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        NewsDto[] newsDtos = mapper.readValue(response, NewsDto[].class);
        return newsDtos;
        //return mapper.reader().forType(NewsDto.class).readValue(response);
        //return mapper.readValue(response, NewsDto.class);
    }
}
