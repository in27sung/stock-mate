package com.stockm8.persistence;

import java.util.List;
import java.util.Map;

import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.StockVO;


public interface OrderDAO {
	 // 주문 등록
    public void insertOrder(OrderVO order) throws Exception;
    
    // 주문 항목 등록
    public void insertOrderItem(List<OrderItemVO> orderItem) throws Exception;
    
    // 모든 재고 목록 조회
    public List<StockVO> findAvailableStocks(int businessId) throws Exception;
    
    // 주문번호 생성
    public String generateOrderNumber() throws Exception;
    
    // 재고 수량 업데이트
    public int updateStockQuantity(Map<String, Object> params) throws Exception;
    
    // 주문 목록
    public List<OrderVO> getOrderList();
    
    // 재고 정보 조회 @@@@@@@@@@@@@@@@@@@@@@@@@@@@ 미사용
    public StockVO getStockById(int stockId) throws Exception;

    // 재고 이력 등록 @@@@@@@@@@@@@@@@@@@@@@@@@@@@ 미사용
    public void insertStockHistory(Map<String, Object> params) throws Exception;
    
    
    
    
    
} // OrderDAO
