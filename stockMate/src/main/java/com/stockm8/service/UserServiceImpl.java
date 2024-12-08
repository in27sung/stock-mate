package com.stockm8.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stockm8.domain.vo.UserVO;
import com.stockm8.persistence.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Inject
	private UserDAO userDAO;

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
	public UserVO getUser(Long userId) throws Exception {
		logger.info(" getuser(String user_id)호출 ");

		return userDAO.getUser(userId);
	}

	@Override
	public void updateUser(UserVO user) throws Exception {
		logger.info("updateuser(UserVO userVO) 실행");

		// DAO 회원정보 수정메서드 호출
		userDAO.updateUser(user);
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
