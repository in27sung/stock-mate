package com.stockm8.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stockm8.domain.vo.ReceivingShipmentVO;
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

	@Override
	public List<ReceivingShipmentVO> getYesterdayReceivingList() throws Exception {
		logger.info("getYesterdayReceivingList() 호출");
		return rdao.selectYesterdayReceivingList();
	}

	@Override
	public List<ReceivingShipmentVO> getTDBYReceivingList() throws Exception {
		logger.info("getTDBYReceivingList() 호출");
		return rdao.selectTDBYReceivingList();
	}

	@Override
	public List<ReceivingShipmentVO> getReceivingHistoryList() throws Exception {
		logger.info("getReceivingHistoryList() 호출");
		return rdao.selectReceivingHistoryList();
	}
	
	@Override
	public List<ReceivingShipmentVO> getHistoryByDateRange(String startDate, String endDate, String keyword) throws Exception{
		logger.info("getHistoryByDateRange() 호출");
	    return rdao.selectHistoryByDateRange(startDate, endDate, keyword);
	}
	
	

	
	
	
	

} // ReceivingServiceImpl end