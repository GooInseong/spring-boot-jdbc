package com.example.demo.src.story.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponse {
    private int storyNum;
    private String storyFile;
    private String storyContent;
    private Timestamp createdAt;
    private int userNum;
    private String userImg;
    private String userNickName;

}
