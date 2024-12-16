package com.stockm8.service;

import com.stockm8.domain.vo.BusinessVO;

public interface BusinessService {
	
	// 회사 등록 
	public void registerBusinessWithUserUpdate(BusinessVO business, Long userId) throws Exception;
	
	// 회사 정보 조회 
	public BusinessVO getBusinessByNumberAndName(String businessNumber, String companyName) throws Exception;
	
}
