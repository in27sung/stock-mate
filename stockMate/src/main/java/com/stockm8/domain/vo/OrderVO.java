package com.stockm8.domain.vo;

import java.sql.Timestamp;
import java.util.List;

import com.stockm8.domain.enums.OrderType;

import lombok.Data;


	@Data
	public class OrderVO {
		 
		public enum OrderType {
		        INBOUND,    // 수주
		        OUTBOUND    // 발주
		}
		
		private int orderId;           // 주문 고유 ID
	    private String orderNumber;    // 주문 번호 (예: ORD-YYYYMMDD-001)
	    private double totalPrice;     // 주문 전체 총 금액
	    private int createdBy;         // 주문을 생성한 사용자 ID
	    private Timestamp createdAt;   // 주문 생성 날짜
	    private Timestamp updatedAt;   // 주문 수정 날짜
	    private OrderType orderType;   // 주문 유형 (INBOUND/OUTBOUND)
	    private String status;         // 주문 상태 (pending, confirmed, cancelled)
	    private List<OrderItemVO> orderItems;  // 주문 항목 리스트
	 
	
   
	   
	    
	    
}// OrderVO
