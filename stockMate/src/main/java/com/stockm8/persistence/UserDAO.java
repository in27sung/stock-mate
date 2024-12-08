package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.vo.UserVO;

public interface UserDAO {
	
	// 회원가입 동작
	public void userJoin(UserVO user);
	
	// 로그인 체크 동작
	public UserVO userLogin(UserVO user);
	
	// 회원정보 조회동작
	public UserVO getUser(Long userId);
	
	// 회원정보 수정동작
	public void updateUser (UserVO user);
	
	// 회원정보 삭제동작
	public int deleteUser (UserVO user);
	
	// 회원정보 목록(list)
	public List<UserVO> getUserList();
	
	/**
     * 유저 정보를 user_id로 조회합니다.
     * @param userId 유저 ID
     * @return UserVO 유저 정보
     * @throws Exception 데이터베이스 작업 중 예외
     */
    UserVO getUserById(Long userId) throws Exception;
	
    /**
     * 유저의 삭제 여부를 가져옵니다.
     * @param userId 유저 ID
     * @return 삭제 여부 (1: 삭제됨, 0: 삭제되지 않음)
     */
    int getIsDeleted(Long userId) throws Exception;

}
