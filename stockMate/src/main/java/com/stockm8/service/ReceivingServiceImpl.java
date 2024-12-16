package com.stockm8.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.ReceivingShipmentVO;
import com.stockm8.domain.vo.StockVO;
import com.stockm8.persistence.ReceivingDAO;
import com.stockm8.persistence.StockDAO;

@Service
public class ReceivingServiceImpl implements ReceivingService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReceivingServiceImpl.class);
	
	@Inject
	private ReceivingDAO rdao;
	
	@Inject
	private StockDAO sdao;

	// 메인 입고 리스트
	@Override
	public List<ReceivingShipmentVO> getReceivingList(int businessId) throws Exception {
		logger.info("getTodayReceivingList() 호출");
		return rdao.selectReceivingList(businessId);
	}

	@Override
	public List<ReceivingShipmentVO> getYesterdayReceivingList(int businessId) throws Exception {
		logger.info("getYesterdayReceivingList() 호출");
		return rdao.selectYesterdayReceivingList(businessId);
	}

	@Override
	public List<ReceivingShipmentVO> getTDBYReceivingList(int businessId) throws Exception {
		logger.info("getTDBYReceivingList() 호출");
		return rdao.selectTDBYReceivingList(businessId);
	}

	@Override
	public List<ReceivingShipmentVO> getReceivingHistoryList(Criteria cri, int businessId) throws Exception {
		logger.info("getReceivingHistoryList() 호출");
		return rdao.selectReceivingHistoryList(cri, businessId);
	}
	
	@Override
	public List<ReceivingShipmentVO> getHistoryByDateRange(String startDate, String endDate, String keyword, Criteria cri, int businessId) throws Exception{
		logger.info("getHistoryByDateRange() 호출");
	    return rdao.selectHistoryByDateRange(startDate, endDate, keyword, cri, businessId);
	}
	
	@Override
	public int getTotalCountBySearch(String startDate, String endDate, String keyword, int businessId) throws Exception {
		logger.info("getTotalCountBySearch() 호출");
		return rdao.selectTotalCountBySearch(startDate, endDate, keyword, businessId);
	}

	@Override
	public int getTotalCount(int businessId) throws Exception {
		logger.info("getTotalCount() 호출");
		return rdao.selectTotalCount(businessId);
	}

	@Override
	public void insertReceiving(int businessId) throws Exception {
		logger.info("insertReceiving() 호출");
		rdao.insertReceiving(businessId);
	}

	
	@Transactional
	@Override
	public int increseStockByBarcode(int businessId, String barcode) throws Exception {
		logger.info("increseStockByBarcode() 호출");
        List<StockVO> stock = rdao.selectQuantityCheckByBarcode(businessId, barcode);
        if (stock == null) {
            return -1; // 유효하지 않은 바코드
        }
        
        int updatedRows = rdao.updateIncreseStock(businessId, barcode);
        if (updatedRows > 0) {
            return rdao.selectStockByBarcode(businessId, barcode); // 증가 후 남은 재고 반환
        } else {
            throw new RuntimeException("재고 업데이트 실패");
        }
    }



} // ReceivingServiceImpl end