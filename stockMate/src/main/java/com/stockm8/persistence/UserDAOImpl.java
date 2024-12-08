package com.stockm8.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.vo.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	@Inject
	private SqlSession sqlSession;

	private static final String NAMESPACE = "com.stockm8.mapper.UserMapper.";

	@Override
	public void userJoin(UserVO user) {
		logger.info("userJoin 실행: " + user);
		sqlSession.insert(NAMESPACE + "insertUser", user);
		logger.info("회원가입 성공!");
	}

	@Override
	public UserVO userLogin(UserVO user) {
		logger.info("UserLogin 실행: " + user);
		UserVO resultVO = sqlSession.selectOne(NAMESPACE + "loginCheck", user);
		logger.info("로그인 결과: " + resultVO);
		return resultVO;
	}

	@Override
	public UserVO getUser(Long userId) {
		logger.info("getUser 실행: user_id = " + userId);
		return sqlSession.selectOne(NAMESPACE + "getUser", userId);
	}

	@Override
	public void updateUser(UserVO user) {
		logger.info("updateUser 실행: " + user);
		sqlSession.update(NAMESPACE + "updateUser", user);
		logger.info("회원정보 수정 완료!");
	}

	@Override
	public int deleteUser(UserVO user) {
		logger.info("deleteUser 실행: " + user);
		return sqlSession.delete(NAMESPACE + "deleteUser", user);
	}

	@Override
	public List<UserVO> getUserList() {
		logger.info("getMemberList 실행");
		return sqlSession.selectList(NAMESPACE + "userList");
	}

	@Override
	public int getIsDeleted(Long user) throws Exception{

		logger.info("getIsDeleted(Long userId) 실행");

		return sqlSession.selectOne(NAMESPACE + "getIsDeleted", user);
	}
	
    @Override
    public UserVO getUserById(Long user) throws Exception {
        return sqlSession.selectOne(NAMESPACE + "getUserById", user);
    }

}
