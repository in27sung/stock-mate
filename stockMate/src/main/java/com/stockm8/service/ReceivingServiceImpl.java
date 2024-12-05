package com.stockm8.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stockm8.domain.ReceivingShipmentVO;
import com.stockm8.persistence.ReceivingDAO;

@Service
public class ReceivingServiceImpl implements ReceivingService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReceivingServiceImpl.class);
	
	@Inject
	private ReceivingDAO rdao;

	// 메인 입고 리스트
	@Override
	public List<ReceivingShipmentVO> getReceivingList() throws Exception {
		logger.info("getTodayReceivingList() 호출");
		return rdao.selectReceivingList();
	}

	
	
	
	

} // ReceivingServiceImpl end
