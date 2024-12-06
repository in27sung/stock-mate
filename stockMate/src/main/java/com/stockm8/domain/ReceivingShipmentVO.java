package com.stockm8.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReceivingShipmentVO {
    private int receivingShipmentNo;   // 입출고 번호
    private Timestamp createdAt;       // 입출고 생성 날짜
    private String status;             // 트랜잭션 상태
    private String transactionType;    // 입출고 유형 (in, out, adjustment, transfer)
    private Integer productId;         // 상품 ID
    private int changeQuantity;        // 입출고 수량
    private String transactionUnit;    // 입출고 단위
    private Integer orderItemId;       // 주문 상세 항목 ID
    private int stockId;               // 재고 ID
    private int warehouseId;           // 관련 창고 ID
    private int createdBy;             // 입출고 생성자 ID
    private String memo;       			 // 작업 사유
    
    // 조인된 데이터
    private String productName;     // 상품명
    private String productDesc;     // 상품 설명
    private String companyName;     // 회사명
    
    
}
