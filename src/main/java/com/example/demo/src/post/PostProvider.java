package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.LikesResponse;
import com.example.demo.src.post.model.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostProvider {
    private PostDao dao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostProvider(PostDao dao) {
        this.dao = dao;
    }

    public List<PostResponse> getPosts (int userNum) throws BaseException{
        try{
           return dao.getPosts(userNum);
        }catch(Exception exception){
            logger.error("App - getPosts Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostResponse getPost (int postNum) throws BaseException{
        try{
            return dao.getPost(postNum);
        }catch(Exception exception){
            logger.error("App - getPost Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<LikesResponse> getLikesUserInfo(int postNum)throws BaseException{
        try{
            return dao.getLikesUserInfo(postNum);
        }catch(Exception exception){
            logger.error("App - getLikesUserInfo Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PostResponse> getPostByTag(String hashTag) throws BaseException{
        try{
            return dao.getPostByTag(hashTag);
        }catch(Exception exception){
            logger.error("App - getPostByTag Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
