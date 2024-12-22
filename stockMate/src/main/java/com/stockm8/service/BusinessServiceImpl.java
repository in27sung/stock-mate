package com.stockm8.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockm8.domain.vo.BusinessVO;
import com.stockm8.persistence.BusinessDAO;
import com.stockm8.persistence.UserDAO;

@Service
public class BusinessServiceImpl implements BusinessService {
	
	private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
 
	@Autowired
	private BusinessDAO businessDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	// 비즈니스 등록 및 사용자 비즈니스 ID 업데이트
	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public void registerBusinessWithUserUpdate(BusinessVO business, Long userId) throws Exception {
	    logger.info("registerBusinessWithUserUpdate() 호출");
	    
	    // 비즈니스 정보 확인
	    if (business == null) {
	        throw new IllegalArgumentException("입력된 비즈니스 정보가 없습니다. 등록하려는 정보를 확인해주세요.");
	    }
	    // 사용자 정보 확인
	    if (userId == null) {
	        throw new IllegalArgumentException("사용자 정보가 확인되지 않았습니다. 로그인 후 다시 시도해주세요.");
	    }
	    
	    // 중복 확인
	    logger.info("사업자 등록 번호 중복 확인: {}", business.getBusinessNumber());
	    int count = businessDAO.checkBusinessNumberExists(business.getBusinessNumber());
	    if (count > 0) {
	        throw new IllegalStateException("이미 등록된 사업자 등록 번호입니다.");
	    }
	    
	    // 비즈니스 등록
	    businessDAO.insertBusiness(business);
	    logger.info("비즈니스 등록 완료: businessNumber={}, businessId={}", business.getBusinessNumber(), business.getBusinessId());

	    if (business.getBusinessId() == null) {
	        throw new IllegalStateException("예상치 못한 오류로 인해 비즈니스 ID가 생성되지 않았습니다.");
	    }

	    int businessId = business.getBusinessId();

	    // 유저의 비즈니스 ID 업데이트
	    int rowsUpdated = userDAO.updateUserBusinessId(userId, businessId);
	    if (rowsUpdated == 0) {
	        throw new RuntimeException("사용자 정보 업데이트에 실패했습니다. 관리자에게 문의해주세요.");
	    }
	    logger.info("사용자 정보 업데이트 성공: userId={}, businessId={}", userId, businessId);
	}

	// 비즈니스 정보를 사업자 등록 번호와 회사명으로 조회
	@Override
	public BusinessVO getBusinessByNumberAndName(String businessNumber, String businessName) throws Exception {
	    logger.info("getBusinessByNumberAndName() 호출");

		return businessDAO.selectBusinessByNumberAndName(businessNumber, businessName);
	}
	
}
