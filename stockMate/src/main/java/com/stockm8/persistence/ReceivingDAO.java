package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.ReceivingShipmentVO;

public interface ReceivingDAO {
	
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectReceivingList() throws Exception;
	
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectYesterdayReceivingList() throws Exception;
		
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectTDBYReceivingList() throws Exception;
	
	// 입고 내역 히스토리
	public List<ReceivingShipmentVO> selectReceivingHistoryList(Criteria cri) throws Exception;
	
	// 입고 내역 검색
	public List<ReceivingShipmentVO> selectHistoryByDateRange(String startDate, String endDate, String keyword, Criteria cri) throws Exception;

	// 검색시 모든 리스트 개수
	public int selectTotalCountBySearch(String startDate, String endDate, String keyword) throws Exception;
	
	// 리스트 모든 개수
	public int selectTotalCount() throws Exception;
	
	// rs 테이블 insert
	public void insertReceiving() throws Exception;
		
} // ReceivingDAO end