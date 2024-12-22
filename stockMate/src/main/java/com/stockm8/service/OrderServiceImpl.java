package com.stockm8.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;  
import com.stockm8.domain.enums.OrderType;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.StockVO;
import com.stockm8.persistence.OrderDAO;
import com.stockm8.persistence.ReceivingDAO;
import com.stockm8.persistence.ShipmentDAO;

@Service
public class OrderServiceImpl implements OrderService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Inject
    private OrderDAO odao;
    
    /**
     * 주문 및 주문 항목을 한번에 처리
     * 수주(OUTBOUND)인 경우 재고도 함께 처리
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertOrderWithItems(OrderVO order, List<OrderItemVO> orderItems, int businessId) throws Exception {
    	logger.info("insertOrderWithItems() 호출");
    	
    	odao.insertOrder(order);
    	
    	for(OrderItemVO item : orderItems) {
            item.setOrderId(order.getOrderId());
        }
    	odao.insertOrderItem(orderItems);
    	
    	//  수주(OUTBOUND)인 경우에만 재고 처리  ==> 발주는 process에서 처리
        if (order.getOrderType() == OrderType.OUTBOUND) {
            for (OrderItemVO item : order.getOrderItems()) {
                updateStockQuantity(item.getStockId(), -item.getQuantity());
            }
        }
	}

    /**
     * 재고 수량 업데이트
     * 성공시 수정된 행 수 반환, 실패시 예외 발생
     */
    @Override
    public void updateStockQuantity(int stockId, int quantity) throws Exception {
        logger.info("updateStockQuantity() 호출 - stockId: {}, quantity: {}", stockId, quantity);
        
        int updatedRows = odao.updateStockQuantity(stockId, quantity);
        if (updatedRows == 0) {
            throw new Exception("재고 수정에 실패했습니다. StockId: " + stockId);
        }
    }
	
    /**
     * 사업자별 가용 재고 목록 조회
     */
    @Override
    public List<StockVO> findAvailableStocks(int businessId) throws Exception {
        logger.info("findAvailableStocks() 호출 - businessId: {}", businessId);
        return odao.findAvailableStocks(businessId);
    }
	
    /**
     * 주문번호 생성
     */
    @Override
    public String generateOrderNumber() throws Exception {
        logger.info("generateOrderNumber() 호출");
        return odao.generateOrderNumber();
    }
	
    /**
     * 주문 목록 조회 (페이징 처리)
     * cri가 null인 경우 기본값 설정
     */
    @Override
    public List<OrderVO> getOrderList(Criteria cri, int businessId) {
        logger.info("getOrderList() 호출 - businessId: {}", businessId);
        
        if (cri == null) {
            cri = new Criteria();
        }
        return odao.getOrderList(cri, businessId);
    }
	
    /**
     * 주문 단건 조회
     */
    @Override
    public OrderVO getOrderById(int orderId) throws Exception {
        logger.info("getOrderById() 호출 - orderId: {}", orderId);
        return odao.getOrderById(orderId);
    }

    /**
     * 주문 상세 항목 목록 조회
     */
    @Override
    public List<OrderItemVO> getOrderItemsByOrderId(int orderId) throws Exception {
        logger.info("getOrderItemsByOrderId() 호출 - orderId: {}", orderId);
        return odao.getOrderItemsByOrderId(orderId);
    }

    /**
     * 가용 재고 체크
     * OUTBOUND(수주) 주문인 경우에만 체크
     */
    @Override
    public boolean checkAvailableStock(OrderItemVO item, OrderType orderType) throws Exception {
        logger.info("checkAvailableStock() 호출 - stockId: {}, orderType: {}", item.getStockId(), orderType);
        
        // 발주(INBOUND)인 경우 체크하지 않음
        if (orderType == OrderType.INBOUND) {
            return true;
        }
        
        // 수주(OUTBOUND)인 경우만 재고 체크
        StockVO stock = odao.findStockWithAvailableQuantity(item.getStockId());
        return stock != null && stock.getAvailableStock() >= item.getQuantity();
    }

    /**
     * 전체 주문 개수 조회 (페이징 계산용)
     */
    @Override
    public int getTotalOrderCount(int businessId) {
        logger.info("getTotalOrderCount() 호출 - businessId: {}", businessId);
        return odao.getTotalOrderCount(businessId);
    }
    
    /**
     * 주문 상세 항목 ID로 주문 ID 조회
     * 이 메서드는 ReceivingService로 이동 예정
     */
    @Override
    public int getOrderIdByOrderItemId(Integer orderItemId) throws Exception {
        logger.info("getOrderIdByOrderItemId() 호출 - orderItemId: {}", orderItemId);
        return odao.getOrderIdByOrderItemId(orderItemId);
    }


} //OrderServiceImpl