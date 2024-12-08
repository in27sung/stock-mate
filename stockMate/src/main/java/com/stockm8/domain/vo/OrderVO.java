package com.stockm8.domain.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderVO {
	private int orderId;           // 주문 고유 ID
    private String orderNumber;    // 주문 번호
    private int warehouseId;       // 주문 대상 창고
    private double totalPrice;     // 주문 전체 총 금액
    private int createdBy;         // 주문을 생성한 사용자 ID
    private Timestamp createdAt;   // 주문 생성 날짜
    private Timestamp updatedAt;   // 주문 수정 날짜
    private String status;         // 주문 상태 (pending, confirmed, cancelled)

}
