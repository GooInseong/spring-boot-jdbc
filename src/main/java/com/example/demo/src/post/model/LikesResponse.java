package com.example.demo.src.post.model;

import com.example.demo.src.users.model.GetUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesResponse {
    private int likesNum;
    private GetUserResponse userResponse;
}
