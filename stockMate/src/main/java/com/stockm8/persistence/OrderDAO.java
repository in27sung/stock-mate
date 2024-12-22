package com.stockm8.persistence;

import java.util.List;
import java.util.Map;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.StockVO;


public interface OrderDAO {
	 // 신규 주문 등록(order 등록할 주문 정보)
	public void insertOrder(OrderVO order) throws Exception;
    
    // 주문 상세 항목 등록(등록할 주문 상세 항목 리스트)
	public void insertOrderItem(List<OrderItemVO> orderItems) throws Exception;
    
    // 사업자별 가용 재고 목록 조회(businessId 사업자 ID 통해서 가용재고목록 리턴)
	public List<StockVO> findAvailableStocks(int businessId) throws Exception;
    
    // 주문번호 생성(형식: ORD-YYYYMMDD-###)
	public String generateOrderNumber() throws Exception;
    
    // 재고 수량 업데이트(파라미터 stockId와 quantity로 업데이트 행수 리턴)
	public int updateStockQuantity(int stockId, int quantity) throws Exception;
    
    // 주문 목록 조회 (페이징 처리)
    // 파라미터 cri 페이징 기준정보, businessId 사업자 ID 주문목록 리턴 
    public List<OrderVO> getOrderList(Criteria cri, int businessId);
    
    // 주문 단건 조회(파라메터 orderId 로 주문정보 리턴)
    // 단건 조회가 왜 필요한지 다시확인 명칭이 문제면 명칭변경
    public OrderVO getOrderById(int orderId) throws Exception;
    
    // 주문 상세 항목 목록 조회(orderId 받아서 상세항목 리턴)
    public List<OrderItemVO> getOrderItemsByOrderId(int orderId) throws Exception;
    
    // 가용재고 정보 조회 or 특정 재고 정보조회 (stockId 받아서 재고정보 리턴)
    public StockVO findStockWithAvailableQuantity(int stockId) throws Exception;
    
    // 전체 주문 개수 조회 (페이징 계산)
    // businessId 받아서 전체 주문수 리턴
    public int getTotalOrderCount(int businessId);
    
    // 오더아이디 (명칭 다시 생각해보고 ReceivingController 에서 사용을 했는데 필요한지 다시확인)
    // 주문 상세 항목 ID로 주문 ID 조회, 입고 검수 처리시 사용 리턴은 주문id
    // 입고 검수 처리라는 특정 비즈니스 로직에서만 사용되므로 
    //             OrderDAO보다는 ReceivingDAO에 위치하는 것이 더 적절 한수씨랑 의논
    public int getOrderIdByOrderItemId(Integer orderItemId) throws Exception;
    
    
} // OrderDAO
