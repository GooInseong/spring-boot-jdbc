package com.example.demo.src.story;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.story.model.PostStoryRequest;
import com.example.demo.src.story.model.StoryResponse;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.REQUEST_ERROR;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private StoryProvider provider;
    private StoryService service;

    public StoryController(StoryProvider provider, StoryService service) {
        this.provider = provider;
        this.service = service;
    }

    // 팔로우 유저 스토리 전체 조회
    @GetMapping("/{userNum}")
    public BaseResponse<List<StoryResponse>> getStories(@PathVariable("userNum")int userNum) {
        try{
            return new BaseResponse<>(provider.getStories(userNum));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 스토리 작성
    @PostMapping("")
    public BaseResponse<String> postStory(@RequestBody PostStoryRequest req){
        String message="Fail to post your story";
        try{
            if(req==null) return new BaseResponse<>(REQUEST_ERROR);
            if(service.postStory(req)) message="Success to post your story!";
            return new BaseResponse<>(message);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 스토리 삭제
    @DeleteMapping("")
    public BaseResponse<String> postStory(@RequestParam int storyNum){
        String message="Fail to delete your story";
        try{
            if(service.deleteStory(storyNum)) message="Success to delete your story!";
            return new BaseResponse<>(message);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 특정 유저 스토리 조회
    @GetMapping("")
    public BaseResponse<List<StoryResponse>> getStoriesByUserNum(@RequestParam int userNum){
        try{
            return new BaseResponse<>(provider.getStoriesByUserNum(userNum));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
