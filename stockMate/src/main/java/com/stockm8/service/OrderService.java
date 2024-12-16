package com.stockm8.service;

import java.util.List;

import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.StockVO;  

public interface OrderService {
	
    // 주문항목을 한꺼번에 처리
    public void insertOrderWithItems(OrderVO order, List<OrderItemVO> orderItems) throws Exception;
    
    //  재고 목록 조회
    public List<StockVO> findAvailableStocks(int businessId) throws Exception;
    
    // 주문번호 생성
    public String generateOrderNumber() throws Exception;
    
    // 재고 수량 업데이트
    public void updateStockQuantity(int stockId, int quantity) throws Exception;
    
    // 주문목록
    public List<OrderVO> getOrderList();

    // 가용 재고 체크 미사용
    public boolean checkAvailableStock(OrderItemVO item) throws Exception;
    
    // 재고 이력 등록 미사용
    public void insertStockHistory(StockVO stock, OrderVO order, int quantityChanged) throws Exception;
    

    
} //OrderService
