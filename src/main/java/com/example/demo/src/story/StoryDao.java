package com.example.demo.src.story;

import com.example.demo.src.story.model.PostStoryRequest;
import com.example.demo.src.story.model.StoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class StoryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<StoryResponse> getStories(int userNum){
        String query="select s.storyNum,s.storyFile,s.storyContent,s.createAt, " +
                "u.userNum,u.userImg,u.userNickName " +
                "from story s " +
                "inner join user u on s.userNum=u.userNum " +
                "where u.userNum in(select following from follow " +
                "where follower=?) and s.status='active'";
        return this.jdbcTemplate.query(query, new RowMapper<StoryResponse>() {
            @Override
            public StoryResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                StoryResponse res= new StoryResponse();
                res.setStoryNum(rs.getInt("storyNum"));
                res.setStoryFile(rs.getString("storyFile"));
                res.setStoryContent(rs.getString("storyContent"));
                res.setCreatedAt(rs.getTimestamp("createAt"));
                res.setUserNum(rs.getInt("userNum"));
                res.setUserImg(rs.getString("userImg"));
                res.setUserNickName(rs.getString("userNickName"));
                return res;
            }
        },userNum);
    }

    public int postStory(PostStoryRequest req){
        String query="insert into story (storyFile, storyContent, userNum) values(?,?,?)";
        Object[] postStoryParam=new Object[]{req.getStoryFile(),req.getStoryContent(),req.getUserNum()};
        return this.jdbcTemplate.update(query,postStoryParam);
    }

    public int changeStatus(int storyNum){
        String query="update story set status='inactive' where storyNum=?";
        return this.jdbcTemplate.update(query,storyNum);
    }

    public List<StoryResponse> getStoriesByUserNum(int userNum){
        String query="select s.storyNum,s.storyFile,s.storyContent,s.createAt, " +
                "u.userNum,u.userImg,u.userNickName " +
                "from story s " +
                "inner join user u on s.userNum=u.userNum " +
                "where u.userNum =? and s.status='active'";
        return this.jdbcTemplate.query(query, new RowMapper<StoryResponse>() {
            @Override
            public StoryResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                StoryResponse res= new StoryResponse();
                res.setStoryNum(rs.getInt("storyNum"));
                res.setStoryFile(rs.getString("storyFile"));
                res.setStoryContent(rs.getString("storyContent"));
                res.setCreatedAt(rs.getTimestamp("createAt"));
                res.setUserNum(rs.getInt("userNum"));
                res.setUserImg(rs.getString("userImg"));
                res.setUserNickName(rs.getString("userNickName"));
                return res;
            }
        },userNum);
    }
}
