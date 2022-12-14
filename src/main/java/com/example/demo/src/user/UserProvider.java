package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.DeleteUserRequest;
import com.example.demo.src.user.model.FollowRequest;
import com.example.demo.src.user.model.GetFollowResponse;
import com.example.demo.src.user.model.GetUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class UserProvider {

    private UserDao dao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao dao) {
        this.dao = dao;
    }

    public GetUserResponse getUserNum(int userNum){
        return dao.getUserTest(userNum);
    }

    public List<GetUserResponse> getUsers() throws BaseException {
        try {
            return dao.getUsers();
        } catch (Exception exception) {
            logger.error("App - getUsers Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserResponse> getUserByNickName(String userNickName) throws BaseException{
        try{
            return dao.getUserByNickName(userNickName);
        }catch(Exception exception){
            logger.error("App - getUserByNickName Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetFollowResponse getFollow(int userNum) throws BaseException{
        try{
            GetFollowResponse response= new GetFollowResponse();
            response.setFollowing(dao.getFollowing(userNum));
            response.setFollower(dao.getFollower(userNum));
            return response;
        }catch(Exception exception){
            logger.error("App - getFollow Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }



    public boolean existUser(int userNum) throws BaseException{
        boolean result= false;
        try{
            if(dao.checkUserNum(userNum)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - existUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean checkPw(DeleteUserRequest req) throws BaseException{
        boolean result= false;
        try{
            if(dao.getPw(req.getUserNum()).equals(req.getUserPw())) result= true;
            return result;
        }catch(Exception exception){
            logger.error("App - existUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean checkFollow(FollowRequest req) throws BaseException{
        boolean result=false;
        try{
            if(dao.checkFollow(req)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - existUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public List<GetUserResponse> getFollowerInfo(int userNum) throws BaseException{
        try{
            return dao.gertFollowerInfo(userNum);
        }catch(Exception exception){
            logger.error("App - getFollowerInfo Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserResponse> getFollowingInfo(int userNum) throws BaseException{
        try{
            return dao.gertFollowingInfo(userNum);
        }catch(Exception exception){
            logger.error("App - getFollowingInfo Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
