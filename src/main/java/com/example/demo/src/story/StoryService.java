package com.example.demo.src.story;

import com.example.demo.config.BaseException;
import com.example.demo.src.story.model.PostStoryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class StoryService {

    private StoryDao dao;

    @Autowired
    public StoryService(StoryDao dao) {
        this.dao = dao;
    }

    final Logger logger= LoggerFactory.getLogger(this.getClass());

    public boolean postStory(PostStoryRequest req) throws BaseException{
        boolean result= false;
        try{
            if(dao.postStory(req)==1) result= true;
            return result;
        }catch (Exception exception){
            logger.error("App - postStory Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean deleteStory(int storyNum) throws BaseException{
        boolean result= false;
        try{
            if(dao.changeStatus(storyNum)==1) result=true;
            return result;
        }catch(Exception exception){
            logger.error("App - deleteStory Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
