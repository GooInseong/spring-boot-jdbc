package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.src.users.model.DeleteUserRequest;
import com.example.demo.src.users.model.PatchUserRequest;
import com.example.demo.src.users.model.FollowRequest;
import com.example.demo.src.users.model.PostUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class UserService {

    private UserDao dao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public boolean createUser(PostUserRequest req) throws BaseException{
        boolean result= false;
        if(dao.postUser(req)==1){
            result=true;
        }
        return result;
    }

    public boolean changeNickName(PatchUserRequest req) throws BaseException{
        boolean result= false;
        if(dao.modifyUser(req)==1) result=true;
        return result;
    }


    public boolean deleteUser(DeleteUserRequest req) throws BaseException{
        boolean result=false;
        if(dao.changeStatus(req)==1) result=true;
        return result;
    }

    public boolean postFollow(FollowRequest req) throws BaseException{
        boolean result=false;
        try{
            if(dao.follow(req)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - postFollow Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean deleteFollow(FollowRequest req) throws BaseException{
        boolean result= false;
        try{
            if(dao.unFollow(req)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - deleteFollow Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
