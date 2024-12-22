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

import com.stockm8.domain.vo.Criteria;
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
   
   
   // 신규 주문 등록(order 등록할 주문 정보)
   @Override
   public void insertOrder(OrderVO order) throws Exception {
       sqlSession.insert(NAMESPACE + "insertOrder", order);
   }
   
   // 주문 상세 항목 등록(등록할 주문 상세 항목 리스트)
   @Override
   public void insertOrderItem(List<OrderItemVO> orderItems) throws Exception {
   	for (OrderItemVO item : orderItems) {
           sqlSession.insert(NAMESPACE + "insertOrderItem", item);
   	}
   }
   
   // 사업자별 가용 재고 목록 조회(businessId 사업자 ID 통해서 가용재고목록 리턴)
   @Override
   public List<StockVO> findAvailableStocks(int businessId) throws Exception {
   	return sqlSession.selectList(NAMESPACE + "findAvailableStocks",businessId);
   }
   
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
   
   // 재고 수량 업데이트(파라미터 stockId와 quantity로 업데이트 행수 리턴)
   @Override
   public int updateStockQuantity(int stockId, int quantity) throws Exception {
       Map<String, Object> params = new HashMap<>();
       params.put("stockId", stockId);
       params.put("quantity", quantity);
       return sqlSession.update(NAMESPACE + "updateStockQuantity", params);
   }
   
   // 재고 정보 조회(stockId 받아서 재고정보 리턴)
   @Override
   public StockVO findStockWithAvailableQuantity(int stockId) throws Exception {
       return sqlSession.selectOne(NAMESPACE + "findStockWithAvailableQuantity", stockId);
   }
   
   // 주문 목록 조회 (페이징 처리)
   // 파라미터 cri 페이징 기준정보, businessId 사업자 ID 주문목록 리턴
   @Override
   public List<OrderVO> getOrderList(Criteria cri, int businessId) {
   	Map<String, Object> paramMap = new HashMap<>();
       paramMap.put("cri", cri);
       paramMap.put("businessId", businessId);
   	return sqlSession.selectList(NAMESPACE + "getOrderList", paramMap);
   }

   // 주문 단건 조회(파라메터 orderId 로 주문정보 리턴)
   @Override
   public OrderVO getOrderById(int orderId) throws Exception {
   	return sqlSession.selectOne(NAMESPACE + "getOrderById", orderId);
   }

   // 주문 상세 항목 목록 조회(orderId 받아서 상세항목 리턴)
   @Override
   public List<OrderItemVO> getOrderItemsByOrderId(int orderId) throws Exception {
   	return sqlSession.selectList(NAMESPACE + "getOrderItemsByOrderId", orderId);
   }

   // 전체 주문 개수 조회 (페이징 계산)
   // businessId 받아서 전체 주문수 리턴
   @Override
   public int getTotalOrderCount(int businessId) {
   	return sqlSession.selectOne(NAMESPACE + "getTotalOrderCount", businessId);
   }
   
   /**
    * 주문 상세 항목 ID로 주문 ID 조회
    * 입고 검수 처리시 사용
    * 이 메서드는 ReceivingDAO로 이동 예정
    * 입고 검수 처리라는 특정 비즈니스 로직에서만 사용되므로 
    * OrderDAO보다는 ReceivingDAO에 위치하는 것이 더 적절
    */
   @Override
   public int getOrderIdByOrderItemId(Integer orderItemId) throws Exception {
       return sqlSession.selectOne(NAMESPACE + "getOrderIdByOrderItemId", orderItemId);
   }
   
} // OrderDAOImpl
