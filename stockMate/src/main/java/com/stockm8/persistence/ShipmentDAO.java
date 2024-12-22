package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.ReceivingShipmentVO;
import com.stockm8.domain.vo.StockVO;

public interface ShipmentDAO {
	
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectShipmentList(int businessId) throws Exception;
	
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectYesterdayShipmentList(int businessId) throws Exception;
		
	// 입고 메인 오늘 들어올 리스트
	public List<ReceivingShipmentVO> selectTDBYShipmentList(int businessId) throws Exception;
	
	// 입고 내역 히스토리
	public List<ReceivingShipmentVO> selectShipmentHistoryList(Criteria cri, int businessId) throws Exception;
	
	// 입고 내역 검색
	public List<ReceivingShipmentVO> selectHistoryByDateRange(String startDate, String endDate, String keyword, Criteria cri, int businessId) throws Exception;

	// 검색시 모든 리스트 개수
	public int selectTotalCountBySearch(String startDate, String endDate, String keyword, int businessId) throws Exception;
	
	// 리스트 모든 개수
	public int selectTotalCount(int businessId) throws Exception;
	
	// recevingShipment 테이블 insert
	public void insertShipment(int businessId, Long userId) throws Exception;
	
    // 바코드로 재고 조회
  	public List<StockVO> selectQuantityCheckByBarcode(int businessId, String barcode, Integer receivingShipmentNo) throws Exception;
 	
   	// 바코드로 재고 감소
  	public int updateIncreseStock(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemId) throws Exception;
 	
  	// 바코드로 재고 감소 실시간 조회
   	public int selectStockByBarcode(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemIdS) throws Exception;
   	
   	// 바코드 찍은 후 발주 수량 감소
   	public int selectReservedQuantity(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemId) throws Exception;
   	
   	// 바코드 찍은 후 제품 이름 추출
   	public ProductVO selectProductNameBarcode(int businessId, String barcode, Integer receivingShipmentNo) throws Exception;
   	
   	// 수량 없을시 완료상태로 변경
   	public void updateShipmentStatusToComplete(int businessId, String barcode, Integer receivingShipmentNo, int orderItemId, Long userId) throws Exception;
   	
   	// 입출고 번호를 누를시 스캔으로가서 특정 리스트 보여주기
 	public List<ReceivingShipmentVO> selectShipmentShipmentNo(int businessId, Integer receivingShipmentNo, int orderItemId) throws Exception;
		
} // ShipmentDAO end