package com.stockm8.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReceivingShipmentVO {
    private int receiving_shipment_no;   // 입출고 번호
    private Timestamp created_at;       // 입출고 생성 날짜
    private String status;             // 트랜잭션 상태
    private String transaction_type;    // 입출고 유형 (in, out, adjustment, transfer)
    private Integer product_id;         // 상품 ID
    private int change_quantity;        // 입출고 수량
    private String transaction_unit;    // 입출고 단위
    private Integer order_itemId;       // 주문 상세 항목 ID
    private int stock_id;               // 재고 ID
    private int warehouse_id;           // 관련 창고 ID
    private int created_by;             // 입출고 생성자 ID
    private String memo;       			 // 작업 사유
    
    
}
