package com.stockm8.persistence;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.vo.BusinessVO;

@Repository
public class BusinessDAOImpl implements BusinessDAO {
	
	// 디비연결 객체 주입 
	@Autowired
	private SqlSession sqlSession;	
	
	private static final String NAMESPACE = "com.stockm8.mapper.BusinessMapper.";

	// 회사 정보 등록 
	@Override
	public void insertBusiness(BusinessVO business) throws Exception {
		
		// mapper 호출 및 실행 
		sqlSession.insert(NAMESPACE + "insertBusiness", business);
	}

    // 회사 정보 조회(businessNumber, companyName)
    @Override
    public BusinessVO selectBusinessByNumberAndName(String businessNumber, String companyName) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("businessNumber", businessNumber);
        params.put("companyName", companyName);

        return sqlSession.selectOne(NAMESPACE + "selectBusinessByNumberAndName", params);
    }
    
	// 회사 중복 확인
	@Override
	public int checkBusinessNumberExists(String businessNumber) {
		return sqlSession.selectOne(NAMESPACE + "checkBusinessNumberExists", businessNumber);
	}
	
}
