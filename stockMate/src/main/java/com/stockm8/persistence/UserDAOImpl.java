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
	public void userJoin(UserVO userVO) {
		logger.info("userJoin 실행: " + userVO);
		sqlSession.insert(NAMESPACE + "insertUser", userVO);
		logger.info("회원가입 성공!");
	}

	@Override
	public UserVO userLogin(UserVO userVO) {
		logger.info("UserLogin 실행: " + userVO);
		UserVO resultVO = sqlSession.selectOne(NAMESPACE + "loginCheck", userVO);
		logger.info("로그인 결과: " + resultVO);
		return resultVO;
	}

	@Override
	public UserVO getUser(Long user_id) {
		logger.info("getUser 실행: user_id = " + user_id);
		return sqlSession.selectOne(NAMESPACE + "getUser", user_id);
	}

	@Override
	public void updateUser(UserVO userVO) {
		logger.info("updateUser 실행: " + userVO);
		sqlSession.update(NAMESPACE + "updateUser", userVO);
		logger.info("회원정보 수정 완료!");
	}

	@Override
	public int deleteUser(UserVO userVO) {
		logger.info("deleteUser 실행: " + userVO);
		return sqlSession.delete(NAMESPACE + "deleteUser", userVO);
	}

	@Override
	public List<UserVO> getUserList() {
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
