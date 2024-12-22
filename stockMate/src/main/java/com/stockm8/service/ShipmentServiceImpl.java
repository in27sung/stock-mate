package com.stockm8.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.ReceivingShipmentVO;
import com.stockm8.domain.vo.StockVO;
import com.stockm8.persistence.ReceivingDAO;
import com.stockm8.persistence.ShipmentDAO;

@Service
public class ShipmentServiceImpl implements ShipmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentServiceImpl.class);
	
	@Inject
	private ShipmentDAO sdao;
	
	@Inject
	private OrderProcessor opService;
	

	// 메인 입고 리스트
	@Override
	public List<ReceivingShipmentVO> getShipmentList(Integer businessId) throws Exception {
		logger.info("getTodayShipmentList() 호출");
		return sdao.selectShipmentList(businessId);
	}

	@Override
	public List<ReceivingShipmentVO> getYesterdayShipmentList(int businessId) throws Exception {
		logger.info("getYesterdayShipmentList() 호출");
		return sdao.selectYesterdayShipmentList(businessId);
	}

	@Override
	public List<ReceivingShipmentVO> getTDBYShipmentList(int businessId) throws Exception {
		logger.info("getTDBYShipmentList() 호출");
		return sdao.selectTDBYShipmentList(businessId);
	}

	@Override
	public List<ReceivingShipmentVO> getShipmentHistoryList(Criteria cri, int businessId) throws Exception {
		logger.info("getShipmentHistoryList() 호출");
		return sdao.selectShipmentHistoryList(cri, businessId);
	}
	
	@Override
	public List<ReceivingShipmentVO> getHistoryByDateRange(String startDate, String endDate, String keyword, Criteria cri, int businessId) throws Exception{
		logger.info("getHistoryByDateRange() 호출");
	    return sdao.selectHistoryByDateRange(startDate, endDate, keyword, cri, businessId);
	}
	
	@Override
	public int getTotalCountBySearch(String startDate, String endDate, String keyword, int businessId) throws Exception {
		logger.info("getTotalCountBySearch() 호출");
		return sdao.selectTotalCountBySearch(startDate, endDate, keyword, businessId);
	}

	@Override
	public int getTotalCount(int businessId) throws Exception {
		logger.info("getTotalCount() 호출");
		return sdao.selectTotalCount(businessId);
	}

	@Override
	public void insertShipment(int businessId, Long userId) throws Exception {
		logger.info("insertShipment() 호출");
		sdao.insertShipment(businessId, userId);
	}

	
	@Transactional
	@Override
	public int increseStockByBarcode(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemId) throws Exception {
	    logger.info("increseStockByBarcode() 호출");
	    
	    // 바코드와 관련된 재고 정보 확인
	    List<StockVO> stock = sdao.selectQuantityCheckByBarcode(businessId, barcode, receivingShipmentNo);
	    logger.info("파라미터: " + stock);
	    
	    if (stock.isEmpty()) {
	        logger.error("유효하지 않은 바코드 또는 재고가 없습니다.");
	        return -1; // 유효하지 않은 바코드
	    }
	    
	    // 재고 증가 시도
	    int updatedRows = sdao.updateIncreseStock(businessId, barcode, receivingShipmentNo, orderItemId);
	    logger.info("업데이트된 행 수: " + updatedRows);
	    
	    if (updatedRows > 0) {
	        // 재고가 정상적으로 증가한 후, 최신 재고 확인
	        int remainingStock = sdao.selectStockByBarcode(businessId, barcode, receivingShipmentNo, orderItemId);
	        logger.info("업데이트 후 남은 재고: " + remainingStock);
	        return remainingStock; // 증가 후 남은 재고 반환
	    } else {
	        logger.error("재고 업데이트 실패: 바코드 또는 입고 번호에 해당하는 재고가 존재하지 않거나 업데이트되지 않았습니다.");
	        throw new RuntimeException("재고 업데이트 실패");
	    }
	}

	@Override
	public int decreaseReservedQuantity(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemId, int orderId, List<OrderItemVO> completedItems) throws Exception {
		logger.info("decreaseReservedQuantity() 호출");
//		opService.processInboundAfterInspection(orderId, completedItems);
		return sdao.selectReservedQuantity(businessId, barcode, receivingShipmentNo, orderItemId);
	}

	@Override
	public ProductVO productNameBarcode(int businessId, String barcode, Integer receivingShipmentNo) throws Exception {
		logger.info("productNameBarcode() 호출");
		
		return sdao.selectProductNameBarcode(businessId, barcode, receivingShipmentNo);
	}
	
	@Override
	public void ShipmentStatusToComplete(int businessId, String barcode, Integer receivingShipmentNo, int orderId, Long userId) {
	    try {
	        // MyBatis 매퍼 호출
	        sdao.updateShipmentStatusToComplete(businessId, barcode, receivingShipmentNo, orderId, userId);
	        
	    } catch (Exception e) {
	        // 예외 처리
	        logger.error("입고 상태 업데이트 오류: " + e.getMessage());
	    }
	}

	@Override
	public List<ReceivingShipmentVO> getReceivingShipmentNo(int businessId, Integer receivingShipmentNo, int orderItemId) throws Exception {
		logger.info("getShipmentShipmentNo() 호출");
		
		return sdao.selectShipmentShipmentNo(businessId, receivingShipmentNo, orderItemId);
	}
	
	
	
	



} // ShipmentServiceImpl end