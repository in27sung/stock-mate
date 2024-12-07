package com.stockm8.domain.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductVO {
    private int productId;         // 고유 상품 ID
    private String name;           // 상품명
    private String barcode;        // 바코드 (NULL 가능)
    private int businessId;        // 사업자 ID
    private int warehouseId;	   // 창고 ID
    private int categoryId;        // 카테고리 ID
    private String baseUnit;       // 기본 단위 (예: 개, 박스)
    private int setSize;           // 세트 크기 (예: 1박스 = 100개)
    private BigDecimal price;      // 상품 기본 단가
    private Timestamp createdAt;   // 상품 등록 일자
    private Timestamp updatedAt;   // 상품 정보 수정 일자
    private String qrCodePath;     // QR 코드 경로
    private String barcodePath;    // 바코드 이미지 저장 경로
    private String description;    // 상품 설명
}
