package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.ReceivingShipmentVO;

public interface ReceivingDAO {
	
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectReceivingList() throws Exception;
	
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectYesterdayReceivingList() throws Exception;
		
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectTDBYReceivingList() throws Exception;

} // ReceivingDAO end
