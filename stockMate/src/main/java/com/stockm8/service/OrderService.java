package com.stockm8.service;

import java.util.List;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;
import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.enums.OrderType;

public interface OrderService {
	
    // 주문 및 주문 항목을 한번에 처리
	//파라메터 order 주문 정보, orderItems 주문 상세 항목 목록, businessId 사업자 ID 입력만 진행
    public void insertOrderWithItems(OrderVO order, List<OrderItemVO> orderItems, int businessId) throws Exception;

    //  사업자별 가용 재고 목록 조회
    // 파라메터 businessId 사업자 ID  리턴 가용 재고 목록
    public List<StockVO> findAvailableStocks(int businessId) throws Exception;
    
    // 주문번호 생성 ( 생성된 주문번호 (ORD-YYYYMMDD-###))
    public String generateOrderNumber() throws Exception;

    // 재고 수량 업데이트
    // 파라메터 stockId 재고 ID, quantity 변경할 수량 만 업데이트
    public void updateStockQuantity(int stockId, int quantity) throws Exception;

    // 주문 목록 조회 (페이징 처리)
    public List<OrderVO> getOrderList(Criteria cri, int businessId);
    
    // 주문 단건 조회
    //  orderId 주문 ID  리턴 주문 정보
    public OrderVO getOrderById(int orderId) throws Exception;
    
    // 주문 상세 항목 목록 조회
    // orderId 주문 ID  리턴  주문 상세 항목 목록
    public List<OrderItemVO> getOrderItemsByOrderId(int orderId) throws Exception;
    
    // 가용 재고 체크
    // OUTBOUND(수주) 주문인 경우에만 체크
    // item 주문 항목, orderType 주문 유형 리턴 재고 충분 여부
    public boolean checkAvailableStock(OrderItemVO item, OrderType orderType) throws Exception;
    
    // 전체 주문 개수 조회 (페이징 계산)
    public int getTotalOrderCount(int businessId);
    
    // 오더 아이디 가져가는 메소드(주문 상세 항목 ID로 주문 ID 조회
    // 입고 검수 처리시 사용
    public int getOrderIdByOrderItemId(Integer orderItemId) throws Exception;
    
} //OrderService
