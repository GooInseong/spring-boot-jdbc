package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.REQUEST_ERROR;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostProvider provider;
    private PostService service;

    @Autowired
    public PostController(PostProvider provider, PostService service) {
        this.provider = provider;
        this.service = service;
    }




    @GetMapping("/")
    public BaseResponse<List<PostResponse>> getPosts(@RequestParam("userNum") int userNum){
        try{
            return new BaseResponse<>(provider.getPosts(userNum));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/{postNum}")
    public BaseResponse<PostResponse> getPost(@PathVariable("postNum") int postNum){
        try{
            return new BaseResponse<>(provider.getPost(postNum));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("")
    public BaseResponse<String> createPost(@RequestBody PostRequest req){
        String message="Fail to create post.";
        try{
            if(req==null) return new BaseResponse<>(REQUEST_ERROR);
            if(service.createPost(req)) message="Success to create post!";
            return new BaseResponse<>(message);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("")
    public BaseResponse<String> modifyPost(@RequestBody PatchRequest req){
        String message="Fail to modify your post";
        try{
            if(req==null) return new BaseResponse<>(REQUEST_ERROR);
            if(service.modifyPost(req)) message="Success to modify your post!";
            return new BaseResponse<>(message);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @DeleteMapping("")
    public BaseResponse<String> deletePost(@RequestBody DeleteRequest req){
        String message="Fail to delete your post.";
        try{
            if(req==null) return new BaseResponse<>(REQUEST_ERROR);
            if(service.deletePost(req)) message="Success to delete your Post!";
            return new BaseResponse<>(message);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/likes")
    public BaseResponse<String> postLikes(@RequestBody LikesRequest req){
        String message="Fail to likes the post!";
        try{
            if(req==null) return new BaseResponse<>(REQUEST_ERROR);
            if(service.likesPost(req)) message="Success to likes the post!";
            return new BaseResponse<>(message);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("/likes")
    public BaseResponse<String> cancelLikes(@RequestBody LikesRequest req){
        String message="Fail to cancel the likes";
        try{
            if(req==null) return new BaseResponse<>(REQUEST_ERROR);
            if(service.cancelLikesPost(req)) message="Success to cancel the likes!";
            return new BaseResponse<>(message);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/likes/{postNum}")
    public BaseResponse<List<LikesResponse>> getLikesInfo(@PathVariable("postNum") int postNum){
        try{
            return new BaseResponse<>(provider.getLikesUserInfo(postNum));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/hashtag-list")
    public BaseResponse<List<PostResponse>> getPostByHashTag(@RequestParam("hashTag") String hashTag){
        try{
            return new BaseResponse<>(provider.getPostByTag(hashTag));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }



}
