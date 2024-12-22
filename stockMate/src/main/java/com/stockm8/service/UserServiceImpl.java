package com.stockm8.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockm8.domain.dto.PendingUserDTO;
import com.stockm8.domain.dto.UpdateUserStatusDTO;
import com.stockm8.domain.vo.BusinessVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.persistence.BusinessDAO;
import com.stockm8.persistence.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;
	
    @Autowired
    private BusinessDAO businessDAO;

	@Override
	public void userJoin(UserVO user) throws Exception {
		userDAO.userJoin(user);
		// userDAO.userJoinOracle(userVO); // 각각의 비지니스 로직을 처리
		// userDAO.userJoinMysql(userVO);
	}

	@Override
	public UserVO userLogin(UserVO user) throws Exception {
		logger.info(" userLogin(UserVO vo) 호출 ");

		// DAO 로그인체크 동작 실행
		UserVO resultVO = userDAO.userLogin(user);
		return resultVO;
	}

	@Override
	public UserVO getUser(Long userId, String password) throws Exception {
		logger.info(" getuser(String user_id)호출 ");

		return userDAO.getUser(userId, password);
	}
	
	@Override
	public List<PendingUserDTO> getPendingUsersWithBusiness() throws Exception {		// TODO Auto-generated method stub
        return userDAO.selectPendingUsersWithBusiness();
	}
	
	@Override
	public List<PendingUserDTO> getStaffByBusinessId(int businessId) throws Exception {
        return userDAO.selectStaffByBusinessId(businessId);
	}

	// 회원 정보 수정 
	@Override
	public void updateUser(UserVO user) throws Exception {
		logger.info("updateuser(UserVO userVO) 실행");

		// DAO 회원정보 수정메서드 호출
		userDAO.updateUser(user);
	}
	
	@Override
	public UserVO getUserInfoById(Long userId) throws Exception {
	    return userDAO.getUserInfoById(userId);
	}
	
	@Override
	public void updatePassword(Long userId, String newPassword) throws Exception {
	    userDAO.updatePassword(userId, newPassword);
	}

	@Override
	public void updateUserStatus(UpdateUserStatusDTO updateUserStatusDTO) {
		userDAO.updateUserStatus(updateUserStatusDTO);
	}

	@Override
	public void updateUserBusinessId(Long userId, int businessId) throws Exception {
		logger.info("updateUserBusinessId() 실행");
		
		// DAO 회원 businessId정보 수정메서드 호출
		userDAO.updateUserBusinessId(userId, businessId);
	}

	@Override
	public String findPassword(String email, String name) throws Exception {
		 logger.info("findPassword 실행: 이메일 = " + email + ", 이름 = " + name);
		    // userDAO에서 비밀번호를 찾아 반환
		   return userDAO.findPassword(email, name);
	}
	
	@Override
	public int deleteUser(UserVO user) throws Exception {
		logger.info(" deleteuser(UserVO dvo) 실행 ");

		return userDAO.deleteUser(user);
	}

	@Override
	public List<UserVO> userList() throws Exception {
		logger.info("userList() 호출");

		return userDAO.getUserList();
	}
	
    @Override
    public UserVO getUserById(Long userId) throws Exception {
		logger.info("getUserById(Long userId) 호출");
		// 사용자 유효성 확인
        return userDAO.getUserById(userId);
    }
	
    @Override
    public int getIsDeleted(Long userId) throws Exception {
		logger.info("getIsDeleted(Long userId)  호출 ");

        return userDAO.getIsDeleted(userId);
    }
    
    
}