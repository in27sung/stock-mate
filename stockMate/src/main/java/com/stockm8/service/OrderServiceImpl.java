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
import com.stockm8.domain.vo.OrderVO;  // OrderItemVO import 제거
import com.stockm8.domain.vo.OrderVO.OrderType;
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
    
    
    @Override
	public void insertOrderWithItems(OrderVO order, List<OrderItemVO> orderItems, int businessId) throws Exception {
    	
    	odao.insertOrder(order);
    	
    	for(OrderItemVO item : orderItems) {
            item.setOrderId(order.getOrderId());
        }
    	odao.insertOrderItem(orderItems);
    	//  수주(OUTBOUND)인 경우에만 재고 처리  ==> 발주는 process에서 처리
        if (order.getOrderType() == OrderType.OUTBOUND) {
            for (OrderItemVO item : order.getOrderItems()) {
                updateStockQuantity(item.getStockId(), item.getQuantity());
            }
        }
	}


    // 재고 수량 업데이트
	@Override
	public void updateStockQuantity(int stockId, int quantity) throws Exception {
		Map<String, Object> params = new HashMap<>();
        params.put("stockId", stockId);
        params.put("quantity", quantity);
        
        int updatedRows = odao.updateStockQuantity(params);
        if (updatedRows == 0) {
            throw new Exception("재고 수정에 실패했습니다. StockId: " + stockId);
        }
		
	}
	
	//  재고 목록 조회
	@Override
	public List<StockVO> findAvailableStocks(int businessId) throws Exception {
		return odao.findAvailableStocks(businessId);
	}
	
	// 주문번호 생성
	@Override
	public String generateOrderNumber() throws Exception {
		return odao.generateOrderNumber();
	}
	
	// 주문목록
	@Override
	public List<OrderVO> getOrderList(Criteria cri, int businessId) {
		// cri가 null인 경우 새로 생성
	    if (cri == null) {
	        cri = new Criteria();
	    }
		
		return odao.getOrderList(cri, businessId);
	}
	

	// 주문 단건 조회
	@Override
	public OrderVO getOrderById(int orderId) throws Exception {
		return odao.getOrderById(orderId);
	}


	// 주문상세항목 조회
	@Override
	public List<OrderItemVO> getOrderItemsByOrderId(int orderId) throws Exception {
		 return odao.getOrderItemsByOrderId(orderId);
	}

	// 가용재고 체크
	@Override
	public boolean checkAvailableStock(OrderItemVO item, OrderType orderType) throws Exception {
	    // 발주(INBOUND)인 경우 체크하지 않음
	    if (orderType == OrderType.INBOUND) {
	        return true;
	    }
	    // 수주(OUTBOUND)인 경우만 재고 체크
	    StockVO stock = odao.getStockById(item.getStockId());
	    return stock != null && stock.getAvailableStock() >= item.getQuantity();
	}

	// 전체 주문 개수 조회 (페이징 계산)
	@Override
	public int getTotalOrderCount(int businessId) {
		return odao.getTotalOrderCount(businessId);
	}


	@Override
	public int getOrderIdByOrderItemId(Integer orderItemId) throws Exception {
	    return odao.getOrderIdByOrderItemId(orderItemId);
	}
	

} //OrderServiceImpl