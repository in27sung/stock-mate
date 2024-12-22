package com.stockm8.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockm8.controller.OrderController;
import com.stockm8.domain.enums.OrderStatus;
import com.stockm8.domain.enums.OrderType;
import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;

@Service
public class OrderProcessor {
    @Inject
    private OrderService orderService;
    @Inject
    private ReceivingService receivingService;
    @Inject
    private ShipmentService shipmentService;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessor.class);

    /**
     * 주문 등록 처리
     * 발주(INBOUND)와 수주(OUTBOUND) 주문을 처리
     * 
     * 수주(OUTBOUND)의 경우:
     * - 가용재고 체크 후 예약재고를 즉시 증가시킴
     * - 출고 대기 상태로 처리
     * 
     * 발주(INBOUND)의 경우:
     * - 입고 대기 상태로 생성
     * - 실제 검수는 나중에 ReceivingService에서 처리됨.
     * 
     * 파라메터 order 주문 정보
     * 파라메터 businessId 사업자 ID
     * 파라메터 userId 사용자 ID
     * 리턴 처리 결과 및 주문번호를 포함한 응답
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> process(OrderVO order, int businessId, Long userId) throws Exception {
        logger.info("주문 처리 시작 - 주문유형: {}", order.getOrderType());
        
        try {
            // 1. 유효성 검사
            validateOrder(order);
            
            // 2. 주문번호 생성 및 기본 정보 설정
            prepareOrder(order);
            
            // 3. 주문 등록
            orderService.insertOrderWithItems(order, order.getOrderItems(), businessId);
            
            // 4. 주문 유형에 따른 후속 처리
            processOrderByType(order, businessId, userId);
            
            // 5. 응답 생성
            return createSuccessResponse(order.getOrderNumber());
            
        } catch (Exception e) {
            logger.error("주문 처리 실패", e);
            throw e;

        }
    }

    /**
     * [현재 미사용]
     * 이전에는 발주 검수 완료 후 재고를 한번에 처리하는 방식이었으나,
     * 현재는 검수 시점에 건별로 처리하는 방식으로 변경으로 참고용으로 놔둠
     * 
     * 현재 프로세스:
     * 1. 검수 시마다 총재고 +1 증가
     * 2. change_quantity가 -1씩 감소
     * 3. 가용재고는 (총재고 - 예약재고)로 자동 계산
     * 4. change_quantity가 0이 되면 주문 완료 처리
     */
    /*
    @Transactional(rollbackFor = Exception.class)
    public void processInboundAfterInspection(int orderId, List<OrderItemVO> completedItems) throws Exception {
        OrderVO order = orderService.getOrderById(orderId);
        
        if (order.getOrderType() != OrderType.INBOUND) {
            throw new IllegalStateException("발주 주문이 아닙니다.");
        }
        
        for (OrderItemVO item : completedItems) {
            orderService.updateStockQuantity(item.getStockId(), -item.getQuantity());
        }
    }
    */

    /**
     * 주문 기본 정보 설정
     * 주문번호를 생성하고 초기 상태를 설정
     */
    private void prepareOrder(OrderVO order) throws Exception {
        order.setOrderNumber(orderService.generateOrderNumber());
        order.setStatus(OrderStatus.PENDING);
        logger.debug("주문 기본 정보 설정 완료 - 주문번호: {}", order.getOrderNumber());
    }

    /**
     * 주문 유형별 처리
     * OUTBOUND(수주): 출고 대기 상태로 처리
     * INBOUND(발주): 입고 대기 상태로 처리
     */
    private void processOrderByType(OrderVO order, int businessId, Long userId) throws Exception {
        if (order.getOrderType() == OrderType.OUTBOUND) {
            shipmentService.insertShipment(businessId, userId);
            logger.debug("출고 대기 상태 생성 완료");
        } else {
            receivingService.insertReceiving(businessId, userId);
            logger.debug("입고 대기 상태 생성 완료");
        }
    }

    /**
     * 성공 응답 생성
     * 처리 결과와 주문번호를 포함한 응답을 생성
     */
    private Map<String, String> createSuccessResponse(String orderNumber) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "주문이 성공적으로 등록되었습니다.");
        response.put("orderNumber", orderNumber);
        logger.info("주문 등록 완료 - 주문번호: {}", orderNumber);
        return response;
    }

    /**
     * 주문 유효성 검사
     * 1. 기본 필드 검증
     * 2. 주문 항목 검증
     * 3. 수주(OUTBOUND)인 경우 재고 가용성 검증
     */
    private void validateOrder(OrderVO order) throws IllegalArgumentException {
        logger.debug("주문 유효성 검사 시작");
        
        // 1. 기본 필드 검증
        if (order == null) {
            throw new IllegalArgumentException("주문 정보가 없습니다.");
        }
        
        if (order.getOrderType() == null) {
            throw new IllegalArgumentException("주문 유형이 누락되었습니다.");
        }
        
        // 2. 주문 항목 검증
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 누락되었습니다.");
        }
        
        // 3. 수주(OUTBOUND)인 경우 재고 검증
        if (order.getOrderType() == OrderType.OUTBOUND) {
            validateOutboundStock(order.getOrderItems());
        }
        
        logger.debug("주문 유효성 검사 완료");
    }

    /**
     * 수주(OUTBOUND) 주문의 재고 가용성 검증
     * 주문 수량이 가용 재고를 초과하지 않는지 확인
     * 
     * 프로세스:
     * 1. 각 주문 항목에 대해 가용 재고 확인
     * 2. 가용 재고 = 총재고 - 예약재고
     * 3. 주문 수량이 가용 재고보다 크면 예외 발생
     */
    private void validateOutboundStock(List<OrderItemVO> items) throws IllegalArgumentException {
        try {
            for (OrderItemVO item : items) {
                // 재고 검증은 OrderService에 위임
                if (!orderService.checkAvailableStock(item, OrderType.OUTBOUND)) {
                    logger.warn("재고 부족 발생 - StockId: {}, 요청수량: {}", 
                        item.getStockId(), item.getQuantity());
                    
                    throw new IllegalArgumentException(
                        String.format("재고 부족 - StockId: %d, 요청수량: %d",
                            item.getStockId(), item.getQuantity())
                    );
                }
                logger.debug("재고 검증 성공 - StockId: {}, 요청수량: {}", 
                    item.getStockId(), item.getQuantity());
            }
        } catch (Exception e) {
            logger.error("재고 검증 중 오류 발생", e);
            throw new IllegalArgumentException("재고 검증 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}// OrderProcessor

