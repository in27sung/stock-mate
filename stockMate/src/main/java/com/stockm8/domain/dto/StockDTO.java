package com.stockm8.domain.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class StockDTO {
    private Integer productId;         // 상품 ID
    private String productName;        // 상품명
    private String productBarcode;     // 바코드
    private String barcodePath;
    private String categoryName;       // 카테고리명
    private Integer warehouseId;       // 창고 ID
    private String warehouseName;      // 창고명
    private int totalQuantity;         // 총 재고 수량
    private int availableStock;        // 사용 가능한 재고
    private String stockQrCodePath;    // 스캔용 QR코드 경로
    private Timestamp updatedAt;  	   // 최근 수정 시간
}