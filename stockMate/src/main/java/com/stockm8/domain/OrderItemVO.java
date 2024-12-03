package com.stockm8.domain;

import lombok.Data;

@Data
public class OrderItemVO {
    private int orderItemId;       // 주문 상세 항목 고유 ID
    private int orderId;           // 주문 ID
    private int productId;         // 주문 상품 ID
    private int quantity;          // 주문 수량
    private double unitPrice;      // 제품 단가
    private double subtotalPrice;  // 해당 상품의 총 금액 (quantity * unitPrice)
    private String remarks;        // 특정 상품에 대한 비고 사항
}
