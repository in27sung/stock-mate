package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.vo.ReceivingShipmentVO;

public interface ShipmentDAO {
	
	// 출고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> todayReceivingList() throws Exception;

} // ReceivingDAO end
