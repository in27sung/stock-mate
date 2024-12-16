package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.ReceivingShipmentVO;
import com.stockm8.domain.vo.StockVO;

public interface ReceivingDAO {
	
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectReceivingList(int businessId) throws Exception;
	
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectYesterdayReceivingList(int businessId) throws Exception;
		
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectTDBYReceivingList(int businessId) throws Exception;
	
	// 입고 내역 히스토리
	public List<ReceivingShipmentVO> selectReceivingHistoryList(Criteria cri, int businessId) throws Exception;
	
	// 입고 내역 검색
	public List<ReceivingShipmentVO> selectHistoryByDateRange(String startDate, String endDate, String keyword, Criteria cri, int businessId) throws Exception;

	// 검색시 모든 리스트 개수
	public int selectTotalCountBySearch(String startDate, String endDate, String keyword, int businessId) throws Exception;
	
	// 리스트 모든 개수
	public int selectTotalCount(int businessId) throws Exception;
	
	// recevingShipment 테이블 insert
	public void insertReceiving(int businessId) throws Exception;
	
    // 바코드로 재고 조회
  	public List<StockVO> selectQuantityCheckByBarcode(int businessId, String barcode) throws Exception;
 	
   	// 바코드로 재고 감소
  	public int updateIncreseStock(int businessId, String barcode) throws Exception;
 	
  	// 바코드로 재고 감소 실시간 조회
   	public int selectStockByBarcode(int businessId, String barcode) throws Exception;
		
} // ReceivingDAO end