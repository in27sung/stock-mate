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

import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;  // OrderItemVO import 제거
import com.stockm8.domain.vo.OrderVO.OrderType;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.StockVO;
import com.stockm8.persistence.OrderDAO;
import com.stockm8.persistence.ReceivingDAO;

@Service
public class OrderServiceImpl implements OrderService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Inject
    private OrderDAO odao;
    
    @Inject
    private ReceivingDAO rdao;
    
    
    @Transactional
    @Override
	public void insertOrderWithItems(OrderVO order, List<OrderItemVO> orderItems) throws Exception {
    	odao.insertOrder(order);
    	for(OrderItemVO item : orderItems) {
            item.setOrderId(order.getOrderId());
        }
    	odao.insertOrderItem(orderItems);
    	processOrderByType(order);
	}



	// 주문 유형에 따라 처리
    private void processOrderByType(OrderVO order) throws Exception {
        if (order.getOrderType() == OrderType.INBOUND) {
            // 수주(출고) 처리
            for (OrderItemVO item : order.getOrderItems()) {
                // 재고 감소
                updateStockQuantity(item.getStockId(), item.getQuantity());
                // 수주(출고)일 때는 예약수량을 양수로 처리해야 함 (+)
                // 재고 이력 기록
//                StockVO stock = odao.getStockById(item.getStockId());
//                insertStockHistory(stock, order, -item.getQuantity());
                
                // TODO: 기존 TODO 항목들 유지
                // - 출고 지시서 생성
                // - 거래명세서 생성
                // - 출고 작업 지시서 생성
                // - 픽킹 리스트 생성
                // - 고객사 알림 발송
                // - 매출 전표 생성
            }
        } else if (order.getOrderType() == OrderType.OUTBOUND) {
            // 발주(입고) 처리
            for (OrderItemVO item : order.getOrderItems()) {
                // 재고 증가
                updateStockQuantity(item.getStockId(), -item.getQuantity());
                // 발주(입)일 때는 예약수량을 음수로 처리해야 함 (-)
                // 재고 이력 기록
//                StockVO stock = odao.getStockById(item.getStockId());
//                insertStockHistory(stock, order, item.getQuantity());
                
                // TODO: 기존 TODO 항목들 유지
                // - 발주서 생성
                // - 입고 예정 등록
                // - 구매 오더 생성
                // - 구매처 발주 확인
                // - 지출 전표 생성
                // - 입고 검수 체크리스트 생성
            }
        }
    }
    

    // 재고 수량 업데이트   => 수정필요
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
	public List<OrderVO> getOrderList() {
		return odao.getOrderList();
	}
	
	// 가용 재고 체크  ================>  미사용
	@Override
	public boolean checkAvailableStock(OrderItemVO item) throws Exception {
		StockVO stock = odao.getStockById(item.getStockId());
		return stock != null && stock.getAvailableStock() >= item.getQuantity();
	}
	
	// 재고 이력 등록  =================> 미사용
	@Override
	public void insertStockHistory(StockVO stock, OrderVO order, int quantityChanged) throws Exception {
		Map<String, Object> params = new HashMap<>();
        params.put("stockId", stock.getStockId());
        params.put("orderId", order.getOrderId());
        params.put("quantityChanged", quantityChanged);
        params.put("actionType", order.getOrderType().toString());
        params.put("createdBy", order.getCreatedBy());
        params.put("remarks", "주문에 의한 재고 변동");
        
        odao.insertStockHistory(params);
		
	}



	

} //OrderServiceImpl