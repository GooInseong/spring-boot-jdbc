package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.users.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/users")
public class UserController {


    private UserProvider provider;

    private UserService service;


    @Autowired
    public UserController(UserProvider provider,UserService service) {
        this.provider = provider;
        this.service = service;

    }

    // 회원 가입
    @PostMapping("")
    public BaseResponse<String> createUser(@RequestBody PostUserRequest req){
        String result="Fail to create user";
        try{
            if(req==null) return new BaseResponse<>(EMPTY_USER_REQUEST);
            if(service.createUser(req)) result="Success to create user!";
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 전체회원, 특정회원 조회
    @GetMapping("")
    public BaseResponse<List<GetUserResponse>> getUsers(@RequestParam(required = false) String userNickName){
        try{
            if(userNickName==null){
                return new BaseResponse<>(provider.getUsers());
            }
            return new BaseResponse<>(provider.getUserByNickName(userNickName));
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    // 닉네임 수정
    @PatchMapping("")
    public BaseResponse<String> changeNickName(@RequestBody PatchUserRequest req){
        String result="Fail to change the NickName";
        try{
            if(req==null) return new BaseResponse<>(MODIFY_REQUEST_EMPTY);
            if(!provider.existUser(req.getUserNum())) return new BaseResponse<>(NONE_EXIST_USER);
            if(service.changeNickName(req)) result="Your NickNane is changed!";
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 유저 삭제
    @DeleteMapping("")
    public BaseResponse<String> deleteUser(@RequestBody DeleteUserRequest req){
        String result="Fail to delete your account";
        System.out.println("번호:"+req.getUserNum()+" 비밀번호:"+req.getUserPw());
        try{
            if(provider.checkPw(req)){
                if(service.deleteUser(req)) result="Success to delete your account!";
            }
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 유저 팔로우 조회
    @GetMapping("/follow/{userNum}")
    public BaseResponse<GetFollowResponse> getFollow(@PathVariable("userNum") int userNum){
        try{
            return new BaseResponse<>(provider.getFollow(userNum));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    // 팔로우 하기
    @PostMapping("/follow")
    public BaseResponse<String> postFollow(@RequestBody FollowRequest req){
        String result="Fail to follow!";
        try{
            if(req==null) return new BaseResponse<>(REQUEST_ERROR);
            if(service.postFollow(req)) result="Success to follow!";
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 팔로우 취소
    @DeleteMapping("/follow")
    public BaseResponse<String> deleteFollow(@RequestBody FollowRequest req){
        String result="Fail to unfollow!";
        try{
            if(provider.checkFollow(req)) result="Success to unfollow!";
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 팔로워 정보 조회
    @GetMapping("/follow/follower/{userNum}")
    public BaseResponse<List<GetUserResponse>> getFollowerList(@PathVariable("userNum") int userNum){
        try{
            return new BaseResponse<>(provider.getFollowerInfo(userNum));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/follow/following/{userNum}")
    public BaseResponse<List<GetUserResponse>> getFollowingList(@PathVariable("userNum") int userNum){
        try{
            return new BaseResponse<>(provider.getFollowingInfo(userNum));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
