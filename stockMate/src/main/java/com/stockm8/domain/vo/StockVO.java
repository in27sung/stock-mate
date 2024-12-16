package com.stockm8.domain.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class StockVO {
    private int stockId; // 고유 재고 ID
    private int productId; // 상품 ID
    private int warehouseId; // 창고 ID
    private int businessId; // 사업자 ID
    private int totalQuantity;  // 창고 내 총 재고 수량
    private int reservedQuantity;  // 예약된 수량
    private int availableStock;  // 사용 가능한 재고 (totalQuantity - reservedQuantity)
    private Timestamp updatedAt; // 최근 수정 시간
    private Timestamp createdAt; // 재고 등록 시간 
    private String description; // 재고 설명
    private Boolean isDeleted;     // 논리 삭제 여부 (true: 삭제됨, false: 활성)

    // 연관 VO 필드
    private ProductVO product;      // 상품 정보
    private CategoryVO category;    // 카테고리 정보
    private WarehouseVO warehouse;  // 창고 정보
    private String warehouseName;   // 창고명

}
