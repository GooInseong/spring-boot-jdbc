package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private int postNum;
    private String postContent;
    private int userNum;
    private String userNickName;
    private String userImg;
    private int likesCnt;
    private int commentsCmt;

}
