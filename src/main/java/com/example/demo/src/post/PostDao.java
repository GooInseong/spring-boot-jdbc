package com.example.demo.src.post;

import com.example.demo.src.post.model.*;
import com.example.demo.src.user.model.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<PostResponse> getPosts(int userNum){
        String query="select p.postNum,p.postContent, " +
                "u.userNum,u.userNickName,u.userImg, " +
                "(select count(l.userNum)from likes l where l.postNum=p.postNum and l.liked=1) 'like', " +
                "(select count(cm.userNum) from comments cm where cm.postNum=p.postNum and cm.status='active') 'comment' " +
                "from post p " +
                "inner join user u on p.userNum=u.userNum " +
                "where u.userNum in(select following from follow " +
                "where follower=?) and p.status='active'";
        return this.jdbcTemplate.query(query, new RowMapper<PostResponse>() {
            @Override
            public PostResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                PostResponse postResponse = new PostResponse();
                postResponse.setPostNum(rs.getInt("postNum"));
                postResponse.setPostContent(rs.getString("postContent"));
                postResponse.setUserNum(rs.getInt("userNum"));
                postResponse.setUserNickName(rs.getString("userNickName"));
                postResponse.setUserImg(rs.getString("userImg"));
                postResponse.setLikesCnt(rs.getInt("like"));
                postResponse.setCommentsCmt(rs.getInt("comment"));
                return postResponse;

            }
        },userNum);

    }

    public PostResponse getPost(int postNum){
        PostResponse response= new PostResponse();
        String query="select p.postNum, p.postContent, " +
                "u.userNum,u.userNickName,u.userImg, " +
                "(select count(l.liked)from likes l where l.postNum=p.postNum) 'like', " +
                "(select count(cm.userNum) from comments cm where cm.postNum=p.postNum and cm.status='active') 'comment' " +
                "from post p " +
                "inner join user u on p.postNum=u.userNum " +
                "where p.postNum=?"
                ;
        return this.jdbcTemplate.queryForObject(query, new RowMapper<PostResponse>() {
            @Override
            public PostResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                PostResponse postResponse = new PostResponse();
                postResponse.setPostNum(rs.getInt("postNum"));
                postResponse.setPostContent(rs.getString("postContent"));
                postResponse.setUserNum(rs.getInt("userNum"));
                postResponse.setUserNickName(rs.getString("userNickName"));
                postResponse.setUserImg(rs.getString("userImg"));
                postResponse.setLikesCnt(rs.getInt("like"));
                postResponse.setCommentsCmt(rs.getInt("comment"));
                return postResponse;
            }
        },postNum);

    }

    public int createPost(PostRequest req){
        String query="insert into post (postContent,userNum) values(?,?)";
        Object[] createPostParam= new Object[]{req.getPostContent(),req.getUserNum()};
        return this.jdbcTemplate.update(query,createPostParam);
    }

    public int modifyPost(PatchRequest req){
        String query="update post set postContent=? where postNum=?";
        Object [] modifyPostParam=new Object[]{req.getPostContent(),req.getPostNum()};
        return this.jdbcTemplate.update(query,modifyPostParam);
    }

    // 게시글 삭제를 위한 비밀번호 조회
    public String getPostUserPw(int postNum){
        String query="select u.userPw from post p " +
                "inner join user u on p.userNum=u.userNum " +
                "where postNum=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String result= rs.getString("userPw");
                return result;
            }
        },postNum);
    }

    public int deletePost(int postNum){
        String query="update post set status='inactive' where postNum=?";
        return this.jdbcTemplate.update(query,postNum);
    }

    public int likesPost(LikesRequest req){
        String query="insert into likes (userNum,postNum) values(?,?)";
        Object[] likesPostParam= new Object[]{req.getUserNum(),req.getPostNum()};
        return this.jdbcTemplate.update(query,likesPostParam);
    }

    public int cancelLikesPost(LikesRequest req){
        String query="update likes set liked=2 where postNum=? and userNum=?";
        Object[] likesPostParam= new Object[]{req.getPostNum(),req.getUserNum()};
        return this.jdbcTemplate.update(query,likesPostParam);
    }

    public List<LikesResponse> getLikesUserInfo(int postNum){
        String query="select l.likesNum, u.userNum, u.userId, u.userName, u.userNickName " +
                "from likes l " +
                "inner join post p on l.postNum=p.postNum " +
                "inner join user u on u.userNum=p.userNum " +
                "where p.postNum=?";
        return this.jdbcTemplate.query(query, new RowMapper<LikesResponse>() {
            @Override
            public LikesResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                LikesResponse res= new LikesResponse();
                GetUserResponse userRes= new GetUserResponse();
                res.setLikesNum(rs.getInt("likesNum"));
                userRes.setUserNum(rs.getInt("userNum"));
                userRes.setUserId(rs.getString("userId"));
                userRes.setUserName(rs.getString("userName"));
                userRes.setUserNickName(rs.getString("userNickName"));
                res.setUserResponse(userRes);
                return res;
            }
        },postNum);

    }


    public List<PostResponse> getPostByTag(String hashTag){
        String query="select p.postNum, p.postContent, " +
                "u.userNum, u.userNickName, u.userImg, " +
                "(select count(l.liked)from likes l where l.postNum=p.postNum) 'like', " +
                "(select count(cm.userNum) from comments cm where cm.postNum=p.postNum and cm.status='active') 'comment' " +
                "from postTags tag " +
                "right join post p on tag.postNum=p.postNum " +
                "inner join user u on p.userNum=u.userNum " +
                "where tag.hashTag=? ";
        String query1="select p.postNum,p.postContent,u.userNum,u.userNickName,u.userImg,(select count(l.liked)from likes l where l.postNum=p.postNum and l.liked=1) 'like' ,(select count(cm.userNum)from comments cm where cm.postNum=p.postNum and p.status='active') 'comment' " +
                "from postTags tag right join post p on tag.postNum=p.postNum inner join user u on p.userNum=u.userNum where tag.hashTag=?";

        return this.jdbcTemplate.query(query, new RowMapper<PostResponse>() {
            @Override
            public PostResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                PostResponse postResponse = new PostResponse();
                postResponse.setPostNum(rs.getInt("postNum"));
                postResponse.setPostContent(rs.getString("postContent"));
                postResponse.setUserNum(rs.getInt("userNum"));
                postResponse.setUserNickName(rs.getString("userNickName"));
                postResponse.setUserImg(rs.getString("userImg"));
                postResponse.setLikesCnt(rs.getInt("like"));
                postResponse.setCommentsCmt(rs.getInt("comment"));
                return postResponse;

            }
        },hashTag);
    }
}

