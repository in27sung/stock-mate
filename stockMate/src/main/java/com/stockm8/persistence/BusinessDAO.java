package com.stockm8.persistence;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.stockm8.domain.vo.BusinessVO;

public interface BusinessDAO {
	
	// 회사 정보 등록 
	public void insertBusiness(BusinessVO business) throws Exception;
	
	// 회사 정보 조회 
	public BusinessVO selectBusinessByNumberAndName(@Param("businessNumber") String businessNumber, @Param("businessName") String businessName) throws Exception;
	
	// 회사 중복 확인
	int checkBusinessNumberExists(String businessNumber);
	
}
