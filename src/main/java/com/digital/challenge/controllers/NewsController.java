package com.digital.challenge.controllers;

import com.digital.challenge.dto.NewsDto;
import com.digital.challenge.services.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/api")
@Tag(name = "News API")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Value("${news.pageSize:https:5}")
    private Integer pageSize;


    @Operation(summary = "Get all news", description = "Returns all news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The news was not found")
    })
    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getNews(Pageable pageable, @RequestParam(required = false) String author, @RequestParam(required = false) String storyTitle){
        Pageable pl = PageRequest.of(pageable.getPageNumber(), Math.min(pageable.getPageSize(), pageSize));
        return new ResponseEntity<>(newsService.getAll(pl, author, storyTitle), HttpStatus.OK);
    }


    @Operation(summary = "Delete news for item", description = "Returns true or false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The news was not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteNews(@PathVariable Long id){
        return new ResponseEntity<>(newsService.delete(id), HttpStatus.OK);
    }


}
