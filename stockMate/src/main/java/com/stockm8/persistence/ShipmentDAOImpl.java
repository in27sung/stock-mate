package com.stockm8.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stockm8.domain.vo.ReceivingShipmentVO;

public class ShipmentDAOImpl implements ShipmentDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentDAOImpl.class);
	
	// 출고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> todayReceivingList() throws Exception {
		logger.info("todayReceivingList() 실행");
		
		return null;
	}

} // ReceivingDAOImpl end
