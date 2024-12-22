package com.stockm8.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.ReceivingShipmentVO;
import com.stockm8.domain.vo.StockVO;

@Mapper
public interface ReceivingService {
	
	// 메인 입고 리스트
	public List<ReceivingShipmentVO> getReceivingList(Integer businessId) throws Exception;
	
	// 메인 어제 입고 리스트
	public List<ReceivingShipmentVO> getYesterdayReceivingList(int businessId) throws Exception;
	
	// 메인 그제 입고 리스트
	public List<ReceivingShipmentVO> getTDBYReceivingList(int businessId) throws Exception;
	
	// 입고 내역 리스트
	public List<ReceivingShipmentVO> getReceivingHistoryList(Criteria cri, int businessId) throws Exception;
	
	// 입고 내역 검색
	public List<ReceivingShipmentVO> getHistoryByDateRange(String startDate, String endDate, String keyword, Criteria cri, int businessId) throws Exception;
	
	// 입고 내역 검색
	public int getTotalCountBySearch(String startDate, String endDate, String keyword, int businessId) throws Exception;
		
	// 글 모든 개수
	public int getTotalCount(int businessId) throws Exception;
	
	// rs 테이블 insert
	public void insertReceiving(int businessId, Long userId) throws Exception;
	
	// 바코드 찍은 후 재고수량 증가
	public int increseStockByBarcode(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemId) throws Exception;
	
	// 바코드 찍은 후 발주 수량 감소
	public int decreaseReservedQuantity(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemIdint, int orderId, List<OrderItemVO> completedItems) throws Exception;
	
	// 바코드 찍은 후 제품 이름 추출
	public ProductVO productNameBarcode(int businessId, String barcode, Integer receivingShipmentNo) throws Exception;
	
	// 수량 없을시 완료상태로 변경
	public void ReceivingStatusToComplete(int businessId, String barcode, Integer receivingShipmentNo, int orderId, Long userId) throws Exception;

	// 입출고 번호를 누를시 스캔으로가서 특정 리스트 보여주기
	public List<ReceivingShipmentVO> getReceivingShipmentNo(int businessId, Integer receivingShipmentNo, int rderItemId) throws Exception;


} // ReceivingService end