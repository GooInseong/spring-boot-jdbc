package com.example.demo.src.story;

import com.example.demo.config.BaseException;
import com.example.demo.src.story.model.StoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class StoryProvider {

    private StoryDao dao;

    @Autowired
    public StoryProvider(StoryDao dao) {
        this.dao = dao;
    }

    final Logger logger= LoggerFactory.getLogger(this.getClass());

    public List<StoryResponse> getStories(int userNum) throws BaseException{
        try{
            return dao.getStories(userNum);
        }catch(Exception exception){
            logger.error("App - getStories Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<StoryResponse> getStoriesByUserNum(int userNum) throws BaseException{
        try{
            return dao.getStoriesByUserNum(userNum);
        }catch(Exception exception){
            logger.error("App - getStories Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
