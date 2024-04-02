package com.digital.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDto {
    @JsonProperty("story_id")
    private Long storyId;
    @JsonProperty("author")
    private String author;
    @JsonProperty("story_title")
    private String storyTitle;
    @JsonProperty("comment_text")
    private String commentText;
    @JsonProperty("story_url")
    private String storyUrl;
    @JsonProperty("created_at")
    private Timestamp createdAt;
}
