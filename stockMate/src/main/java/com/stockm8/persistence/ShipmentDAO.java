package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.ShipmentVO;

public interface ShipmentDAO {
	
	// �԰� ���� ���� ���� ����Ʈ
		public List<ShipmentVO> todayReceivingList() throws Exception;

} // ReceivingDAO end
