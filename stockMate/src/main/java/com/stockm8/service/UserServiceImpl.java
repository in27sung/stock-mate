package com.stockm8.service;

import java.util.List;

import javax.inject.Inject;

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
	public void userJoin(UserVO userVO) {
		userDAO.userJoin(userVO);
		// userDAO.userJoinOracle(userVO); // 각각의 비지니스 로직을 처리
		// userDAO.userJoinMysql(userVO);
	}

	@Override
	public UserVO userLogin(UserVO userVO) {
		logger.info(" userLogin(UserVO vo) 호출 ");

		// DAO 로그인체크 동작 실행
		UserVO resultVO = userDAO.userLogin(userVO);
		return resultVO;
	}

	@Override
	public UserVO getuser(String userId) {
		logger.info(" getuser(String user_id)호출 ");

		return userDAO.getUser(userId);
	}

	@Override
	public void updateuser(UserVO userVO) {
		logger.info("updateuser(UserVO userVO) 실행");

		// DAO 회원정보 수정메서드 호출
		userDAO.updateUser(userVO);
	}

	@Override
	public int deleteuser(UserVO userVO) {
		logger.info(" deleteuser(UserVO dvo) 실행 ");

		return userDAO.deleteUser(userVO);
	}

	@Override
	public List<UserVO> userList() {
		logger.info(" userList()  호출 ");

		return userDAO.getUserList();
	}
}
