package com.example.demo.src.user;

import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetUserResponse getUserTest(int userNum){
        GetUserResponse response=new GetUserResponse();
        response.setUserNum(1);
        return response;

    }

    // 유저 조회
    public List<GetUserResponse> getUsers(){
        String query="select userNum, userId, userName, userNickName from user where status='active'";
        return this.jdbcTemplate.query(query, new RowMapper<GetUserResponse>() {
            @Override
            public GetUserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetUserResponse res= new GetUserResponse();
                res.setUserNum(rs.getInt("userNum"));
                res.setUserId(rs.getString("userId"));
                res.setUserName(rs.getString("userName"));
                res.setUserNickName(rs.getString("userNickName"));
                return res;
            }
        });
    }

    // 닉네임으로 유저 조회
    public  List<GetUserResponse> getUserByNickName(String userNickName){
        String query="select userNum, userId, userName, userNickName from user where status='active' and userNickName=?";
        return this.jdbcTemplate.query(query, new RowMapper<GetUserResponse>() {
            @Override
            public GetUserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetUserResponse res= new GetUserResponse();
                res.setUserNum(rs.getInt("userNum"));
                res.setUserId(rs.getString("userId"));
                res.setUserName(rs.getString("userName"));
                res.setUserNickName(rs.getString("userNickName"));
                return res;
            }
        },userNickName);
    }

    // 유저 등록
    public int postUser(PostUserRequest req){
        String query="insert into user (userId,userName,userPw,userNickName) values(?,?,?,?)";
        Object[] postQueryParam= new Object[]{req.getUserId(),req.getUserName(),req.getUserPw(),req.getUserNickName()};
        return this.jdbcTemplate.update(query,postQueryParam);
    }

    // 닉네임 수정
    public int modifyUser(PatchUserRequest req){
        String query="update user set userNickName=? where userNum=?";
        Object[] updateQueryParam= new Object[]{req.getChangeNickName(),req.getUserNum()};
        return this.jdbcTemplate.update(query,updateQueryParam);
    }

    // 유저 상태 병경
    public int changeStatus(DeleteUserRequest req){
        String query="update user set status='inactive' where userNum=?";
        return this.jdbcTemplate.update(query,req.getUserNum());
    }

    // 팔로잉 조회
    public List<Integer> getFollowing(int userNum){
        String query="select following from follow where follower=?";
        return this.jdbcTemplate.query(query, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer following= rs.getInt("following");
                return following;
            }
        },userNum);
    }
    // 팔로워 조회
    public List<Integer> getFollower(int userNum){
        String query="select follower from follow where following=?";
        return this.jdbcTemplate.query(query, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer follower= rs.getInt("follower");
                return follower;
            }
        },userNum);
    }

    // 팔로워 정보 조회
    public List<GetUserResponse> gertFollowerInfo(int userNum){
        String query="select u.userNum, u.userId, u.userName, u.userNickName from follow f " +
                "inner join user u on f.following=u.userNum " +
                "where f.follower=?";
        return this.jdbcTemplate.query(query,  (rs,rowNum) -> new GetUserResponse(
                rs.getInt("u.userNum"),
                rs.getString("u.userId"),
                rs.getString("u.userName"),
                rs.getString("u.userNickNAme"))
                ,userNum);
    }
    // 팔로잉 정보 조회
    public List<GetUserResponse> gertFollowingInfo(int userNum){
        String query="select u.userNum, u.userId, u.userName, u.userNickName from follow f " +
                "inner join user u on f.follower=u.userNum " +
                "where f.following=?";
        return this.jdbcTemplate.query(query,  (rs,rowNum) -> new GetUserResponse(
                        rs.getInt("u.userNum"),
                        rs.getString("u.userId"),
                        rs.getString("u.userName"),
                        rs.getString("u.userNickNAme"))
                ,userNum);
    }

    // 팔로잉
    public int follow(FollowRequest req){
        String query="insert into follow (follower,following) values(?,?)";
        Object[] postQueryParam=new Object[]{req.getFollower(),req.getFollowing()};
        return this.jdbcTemplate.update(query,postQueryParam);
    }


    // 팔로우 취소
    public int unFollow(FollowRequest req){
        String query="delete from follow where follower=? and following=?";
        Object[] param=new Object[]{req.getFollower(),req.getFollowing()};
        return this.jdbcTemplate.update(query,int.class,param);
    }


    // 팔로우 조회
    public int checkFollow(FollowRequest req){
        String query="select exists(select following from follow where follower=? and following=?)";
        Object[] param=new Object[]{req.getFollower(),req.getFollowing()};
        return this.jdbcTemplate.queryForObject(query,int.class,param);
    }

    // 비밀 번호 가져오기
    public String getPw(int userNum){
        String query="select userPw from user where status='active' and userNum=?";
        return this.jdbcTemplate.queryForObject(query,String.class,userNum);
    }

    // 유저 번호 조회
    public int checkUserNum(int userNum){
        String query="select exists(select userNum from user where status='active' and userNum=?)";
        System.out.println(this.jdbcTemplate.queryForObject(query,int.class,userNum));
        return this.jdbcTemplate.queryForObject(query,int.class,userNum);
    }
    
}
