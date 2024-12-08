package com.stockm8.domain.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReceivingShipmentVO {
    private int receivingShipmentNo;         // 고유 트랜잭션 ID
    private String transactionType;    // 입출고 유형 (in, out, adjustment, transfer)
    private Timestamp createdAt;       // 입출고 생성 날짜
    private String status;             // 트랜잭션 상태
    private Integer productId;         // 관련 상품 ID
    private int changeQuantity;        // 입출고 수량
    private String transactionUnit;    // 입출고 단위
    private Integer orderItemId;       // 주문 상세 항목 ID
    private int stockId;               // 재고 ID
    private int warehouseId;           // 관련 창고 ID
    private int createdBy;             // 입출고 생성자 ID
    private String memo;        // 작업 사유
    private Timestamp updatedAt; // 업데이트 날짜
    
//  조인된 VO
    private String productName;
    private String productDesc;
    private String companyName;
}
