package com.stockm8.service;

import java.util.List;

import com.stockm8.domain.dto.PendingUserDTO;
import com.stockm8.domain.dto.UpdateUserStatusDTO;
import com.stockm8.domain.vo.UserVO;

public interface UserService {

   //실행하려는 동작 추상메서드로 구현
   
   // 회원가입
   public void userJoin(UserVO user) throws Exception;
   
   // 로그인 체크
   public UserVO userLogin(UserVO user) throws Exception;
   
   // 회원정보 조회
   public UserVO getUser(Long userId,String password) throws Exception;
   
   // 조건에 맞는 pending 회원 리스트와 사업자 정보 가져오기
   public List<PendingUserDTO> getPendingUsersWithBusiness() throws Exception;
   
   // 같은 회사의 pending 회원 리스트와 사업자 정보 가져오기
   public List<PendingUserDTO> getStaffByBusinessId(int businessId) throws Exception;
   
   // 회원정보 수정
   public void updateUser(UserVO user) throws Exception;
   
   public UserVO getUserInfoById(Long userId) throws Exception;
   
   void updatePassword(Long userId, String newPassword) throws Exception;
   
   public void updateUserStatus(UpdateUserStatusDTO updateUserStatusDTO);

   // 회원정보에서 비밀번호 수정
   public void updateUserBusinessId(Long userId, int businessId) throws Exception;
   
   // 비밀번호 찾기
   public String findPassword (String email, String name) throws Exception;
	
   // 회원정보 삭제
   public int deleteUser(UserVO user) throws Exception;
   
   // 회원정보 목록조회
   public List<UserVO> userList() throws Exception;
   
   /**
    * 유저 정보를 user_id로 조회합니다.
    * @param userId 유저 ID
    * @return UserVO 유저 정보
    * @throws Exception 서비스 작업 중 예외
    */
   UserVO getUserById(Long userId) throws Exception;
   
    /**
     * 유저의 삭제 여부를 가져옵니다.
     * @param userId 유저 ID
     * @return 삭제 여부 (1: 삭제됨, 0: 삭제되지 않음)
     */
   int getIsDeleted(Long userId) throws Exception;


}