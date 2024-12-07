package com.stockm8.domain.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReceivingShipmentVO {
    private int transactionId;         // 고유 트랜잭션 ID
    private int stockId;               // 재고 ID
    private Integer productId;         // 관련 상품 ID
    private Integer orderItemId;       // 주문 상세 항목 ID
    private int warehouseId;           // 관련 창고 ID
    private int changeQuantity;        // 입출고 수량
    private String transactionUnit;    // 입출고 단위
    private String transactionType;    // 입출고 유형 (in, out, adjustment, transfer)
    private int createdBy;             // 입출고 생성자 ID
    private Timestamp createdAt;       // 입출고 생성 날짜
    private String status;             // 트랜잭션 상태
    private String description;        // 작업 사유
}
