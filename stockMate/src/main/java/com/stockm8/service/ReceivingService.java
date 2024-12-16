package com.stockm8.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.ReceivingShipmentVO;

@Mapper
public interface ReceivingService {
	
	// 메인 입고 리스트
	public List<ReceivingShipmentVO> getReceivingList(int businessId) throws Exception;
	
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
	public void insertReceiving(int businessId) throws Exception;
	
	// 바코드 찍은 후 재고수량 증가
	public int increseStockByBarcode(int businessId, String barcode) throws Exception;

} // ReceivingService end