package com.stockm8.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.ReceivingShipmentVO;

@Mapper
public interface ReceivingService {
	
	// 메인 입고 리스트
	public List<ReceivingShipmentVO> getReceivingList() throws Exception;
	
	// 메인 어제 입고 리스트
	public List<ReceivingShipmentVO> getYesterdayReceivingList() throws Exception;
	
	// 메인 그제 입고 리스트
	public List<ReceivingShipmentVO> getTDBYReceivingList() throws Exception;
	
	// 입고 내역 리스트
	public List<ReceivingShipmentVO> getReceivingHistoryList(Criteria cri) throws Exception;
	
	// 입고 내역 검색
	public List<ReceivingShipmentVO> getHistoryByDateRange(String startDate, String endDate, String keyword, Criteria cri) throws Exception;
	
	// 입고 내역 검색
	public int getTotalCountBySearch(String startDate, String endDate, String keyword) throws Exception;
		
	// 글 모든 개수
	public int getTotalCount() throws Exception;
	
	// rs 테이블 insert
	public void insertReceiving() throws Exception;

} // ReceivingService end