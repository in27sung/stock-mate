package com.stockm8.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.dto.PendingUserDTO;
import com.stockm8.domain.dto.UpdateUserStatusDTO;
import com.stockm8.domain.vo.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	@Inject
	private SqlSession sqlSession;

	private static final String NAMESPACE = "com.stockm8.mapper.UserMapper.";

	@Override
	public void userJoin(UserVO user) throws Exception{
		logger.info("userJoin 실행: " + user);
		sqlSession.insert(NAMESPACE + "insertUser", user);
		logger.info("회원가입 성공!");
	}

	@Override
	public UserVO userLogin(UserVO user) throws Exception{
		logger.info("UserLogin 실행: " + user);
		UserVO resultVO = sqlSession.selectOne(NAMESPACE + "loginCheck", user);
		logger.info("로그인 결과: " + resultVO);
		return resultVO;
	}

	@Override
	public UserVO getUser(Long userId, String password) throws Exception{
	    logger.info("getUser 실행: user_id = " + userId);

	    // 비밀번호가 일치하는 사용자 정보 조회
	    Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("password", password);

	    // 비밀번호와 사용자 ID를 사용하여 조회
	    return sqlSession.selectOne(NAMESPACE + "getUser", params);
	}
	
	@Override
	public UserVO getUserInfoById(Long userId) throws Exception {
	    return sqlSession.selectOne("UserMapper.getUserInfoById", userId);
	}

	@Override
	public List<PendingUserDTO> selectPendingUsersWithBusiness() throws Exception{
		return sqlSession.selectList(NAMESPACE + "selectPendingUsersWithBusiness");
	}
	
	@Override
	public List<PendingUserDTO> selectStaffByBusinessId(int businessId) throws Exception{
		return sqlSession.selectList(NAMESPACE + "selectStaffByBusinessId", businessId);
	}

	@Override
	public void updateUser(UserVO user) throws Exception{
		 logger.info("updateUser 실행: " + user);  // 전달되는 userVO 객체 확인
		   sqlSession.update(NAMESPACE + "updateUser", user);
		
		   logger.info("회원정보 수정 완료!");
	}
	
    // 회원 승인 여부 수정 
	@Override
	public void updateUserStatus(UpdateUserStatusDTO updateUserStatusDTO) throws Exception{
	    // DTO에서 데이터를 Map으로 변환
	    Map<String, Object> params = new HashMap<>();
	    params.put("approvedUserId", updateUserStatusDTO.getApprovedUserId());
	    params.put("userStatus", updateUserStatusDTO.getUserStatus()); // 'status' -> 'userStatus'로 변경
	    params.put("userId", updateUserStatusDTO.getUserId());

	    // MyBatis 쿼리 호출
	    sqlSession.update(NAMESPACE + "updateUserStatus", params);
	}

	@Override
	public void updatePassword(Long userId, String newPassword) throws Exception{
	    logger.info("updatePassword 실행: userId = " + userId);

	    Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("newPassword", newPassword);

	    sqlSession.update(NAMESPACE + "updatePassword", params);
	    logger.info("비밀번호 변경 완료");
	}

	
	@Override
	public int updateUserBusinessId(Long userId, int businessId) throws Exception{
	    Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("businessId", businessId);
	    
	    // 반환 값으로 영향을 받은 행(row) 수를 반환합니다.
	    return sqlSession.update(NAMESPACE + "updateUserBusinessId", params);
	}

	@Override
    public String findPassword(String email, String name) throws Exception {
        logger.info("findPassword 실행: 이메일 = " + email + ", 이름 = " + name);

        // 이메일과 이름을 조건으로 사용자 조회
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("name", name);

        // DB에서 사용자를 조회
        return sqlSession.selectOne(NAMESPACE + "findPassword", Map.of("email", email, "name", name));
    }

	@Override
	public int deleteUser(UserVO user) throws Exception{
		logger.info("deleteUser 실행: " + user);
		return sqlSession.delete(NAMESPACE + "deleteUser", user);
	}

	@Override
	public List<UserVO> getUserList() throws Exception{
		logger.info("getMemberList 실행");
		return sqlSession.selectList(NAMESPACE + "userList");
	}

	@Override
	public int getIsDeleted(Long userId) throws Exception{

		logger.info("getIsDeleted(Long userId) 실행");

		return sqlSession.selectOne(NAMESPACE + "getIsDeleted", userId);
	}
	
    @Override
    public UserVO getUserById(Long userId) throws Exception {
        return sqlSession.selectOne(NAMESPACE + "getUserById", userId);
    }

}