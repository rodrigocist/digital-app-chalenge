package com.digital.challenge.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "information")
public class News {

    @Id
    private Long storyId;
    private String author;
    @Column(columnDefinition="TEXT")
    private String storyTitle;
    @Column(columnDefinition="TEXT")
    private String commentText;
    @Column(columnDefinition="TEXT")
    private String storyUrl;
    private Timestamp createdAt;
    @Column(name = "visible", nullable = false, columnDefinition = "boolean default true")
    private Boolean visible = true;


}
