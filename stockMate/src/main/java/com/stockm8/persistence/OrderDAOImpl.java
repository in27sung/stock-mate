package com.stockm8.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.StockVO;

@Repository
public class OrderDAOImpl implements OrderDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);
    
    @Inject
    private SqlSession sqlSession;
    
    private static final String NAMESPACE = "com.stockm8.mapper.OrderMapper.";
    
    
    // 주문 등록
    @Override
    public void insertOrder(OrderVO order) throws Exception {
        sqlSession.insert(NAMESPACE + "insertOrder", order);
    }
    
    // 주문 항목 등록
    @Override
    public void insertOrderItem(List<OrderItemVO> orderItems) throws Exception {
    	for (OrderItemVO item : orderItems) {
    
        sqlSession.insert(NAMESPACE + "insertOrderItem", item);
    	}
    }
    
//	public void insertOrderWithItems(OrderVO order) throws Exception {
//		try {
//		
//			logger.info("주문 기본 정보 저장 시작");
//			// 주문 마스터 등록(Order 와 Orderitem 을 한번에 처리)
//	        sqlSession.insert(NAMESPACE + "insertOrder", order);
//	        logger.info("주문 기본 정보 저장 완료, orderId: {}", order.getOrderId());
//	        
//	        
//	       // 생성된 주문 ID로 각 주문 항목 등록
//	        for (OrderItemVO item : order.getOrderItems()) {
//	        	logger.info("주문 항목 저장 시작: productId={}, quantity={}", 
//	                       item.getProductId(), item.getQuantity());
//	            item.setOrderId(order.getOrderId());
//	            sqlSession.insert(NAMESPACE + "insertOrderItem", item);
//	            logger.info("주문 항목 저장 완료");
//	        }
//		
//		}  catch(Exception e) {
//			logger.error("주문 정보 저장 중 오류 발생", e);
//	        throw e;
//			
//		}
//	}
	// 모든 재고 목록 조회
	@Override
	public List<StockVO> findAvailableStocks(int businessId) throws Exception {
		return sqlSession.selectList(NAMESPACE + "findAvailableStocks",businessId);
	}
	
	
	//주문번호 생성
	/**
	 * 새로운 주문번호 생성
	 * 동시성 제어를 위해 synchronized 키워드 사용
	 * 형식: ORD-YYYYMMDD-###
	 */
    @Override
    public synchronized String generateOrderNumber() throws Exception {
        // 현재 날짜 형식 지정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        
        // 해당 날짜의 마지막 주문번호 조회
        String lastOrderNumber = sqlSession.selectOne(NAMESPACE + "getLastOrderNumberForDate", date);
        
        // 시퀀스 번호 생성
        int sequence = 1;
        if (lastOrderNumber != null && !lastOrderNumber.isEmpty()) {
            String sequenceStr = lastOrderNumber.substring(lastOrderNumber.lastIndexOf("-") + 1);
            sequence = Integer.parseInt(sequenceStr) + 1;
        }
        
        // 주문번호 생성 및 반환
        return String.format("ORD-%s-%03d", date, sequence);
    }
    
	
	// 재고 수량 업데이트 =>@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  문제 수정해야함
	@Override
	public int updateStockQuantity(Map<String, Object> params) throws Exception {
		return sqlSession.update(NAMESPACE + "updateStockQuantity", params);
	}
	
	
	// 재고 정보 조회  ==== > 미사용
	@Override
	public StockVO getStockById(int stockId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "getStockById", stockId);
	}

	
	// 재고 이력 등록 =====> 미사용
	@Override
	public void insertStockHistory(Map<String, Object> params) throws Exception {
		sqlSession.insert(NAMESPACE + "insertStockHistory", params);
		
	}
	
	//오더 목록
	@Override
	public List<OrderVO> getOrderList() {
		return sqlSession.selectList(NAMESPACE+ "orderList");
	}



	
} // OrderImpl