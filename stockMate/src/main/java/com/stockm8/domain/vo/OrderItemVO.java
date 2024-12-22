package com.stockm8.domain.vo;

import java.sql.Timestamp;

import com.stockm8.domain.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderItemVO {
	// 기본 정보
    private int orderItemId;      // 주문 상세 항목 고유 ID
    private int orderId;          // 주문 ID
    private int stockId;          // 재고 ID
    private int productId;        // 상품 ID
    private int warehouseId;      // 창고 ID
    
    // 수량/가격 정보
    private int quantity;         // 주문 수량
    private int changeQuantity;   // 검수 후 변경된 수량
    private double unitPrice;     // 제품 단가
    private double subtotalPrice; // 항목 총 금액 (quantity * unitPrice)
    
    // 상태 정보
    private OrderStatus status;   // 처리 상태 (PENDING, COMPLETED, CANCELLED)
    
    // 부가 정보
    private String remarks;       // 비고사항
    
    // 생성/수정 정보
    private Timestamp createdAt;  // 생성 시간
    private Timestamp updatedAt;  // 수정 시간
    
    // JOIN 정보 (연관 테이블에서 가져오는 정보)
    private String productName;     // 상품명
    private String productBarcode;  // 상품 바코드
    private String warehouseName;   // 창고명
    private String baseUnit;        // 기본 단위
    private String shipmentStatus;    // 입출고 상태
    
    // ProductVO 연관 객체
    private ProductVO product;      // 상품 정보
    
    
    
} //OrderItemVO
