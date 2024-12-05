package com.stockm8.service;

import java.util.List;

import com.stockm8.domain.ReceivingShipmentVO;


public interface ReceivingService {
	
	// 메인 입고 리스트
	public List<ReceivingShipmentVO> getReceivingList() throws Exception;
	
	

} // ReceivingService end
