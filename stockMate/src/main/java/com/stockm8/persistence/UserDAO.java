package com.stockm8.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.stockm8.domain.vo.UserVO;


public interface UserDAO {

    // 회원가입 동작
    public void userJoin(UserVO user);

    // 로그인 체크 동작
    public UserVO userLogin(UserVO user);

    // 회원정보 조회동작
    public UserVO getUser(Long userId , String password); //

    // 회원정보 수정동작
    public void updateUser (UserVO user);
    
    // 비밀번호 찾기 동작
    public String findPassword(String email,String name) throws Exception; 
    
    // 
    public UserVO getUserInfoById(Long userId) throws Exception;
    
    //
    void updatePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);

    // 사용자의 businessId 수정
    int updateUserBusinessId(@Param("userId") Long userId, @Param("businessId") int businessId);

    
    // 회원정보 삭제동작
    public int deleteUser (UserVO user);

    // 회원정보 목록(list)
    public List<UserVO> getUserList();

    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    UserVO getUserById(@Param("userId")Long userId) throws Exception;


    int getIsDeleted(Long userId) throws Exception;

}