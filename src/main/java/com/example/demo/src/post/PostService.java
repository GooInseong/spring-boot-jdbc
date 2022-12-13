package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.DeleteRequest;
import com.example.demo.src.post.model.LikesRequest;
import com.example.demo.src.post.model.PatchRequest;
import com.example.demo.src.post.model.PostRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostService {

    private PostDao dao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostService(PostDao dao) {
        this.dao = dao;
    }

    public boolean createPost(PostRequest req) throws BaseException{
        boolean result= false;
        try{
            if(dao.createPost(req)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - createPost Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean modifyPost(PatchRequest req) throws BaseException{
        boolean result= false;
        try{
            if(dao.modifyPost(req)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - modifyPost Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean deletePost(DeleteRequest req) throws BaseException{
        boolean result= false;
        try{
            String getPw=dao.getPostUserPw(req.getPostNum());
            if(getPw.equals(req.getUserPw())){
                if(dao.deletePost(req.getPostNum())==1) result= true;
            }
            return result;
        }catch(Exception exception){
            logger.error("App - deletePost Service Errkr",exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean likesPost(LikesRequest req) throws BaseException{
        boolean result= false;
        try{
            if(dao.likesPost(req)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - likesPost Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean cancelLikesPost(LikesRequest req) throws BaseException{
        boolean result= false;
        try{
            if(dao.cancelLikesPost(req)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - likesPost Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
