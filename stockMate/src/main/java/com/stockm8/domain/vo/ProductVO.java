package com.stockm8.domain.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.stockm8.domain.dto.QRCodeDTO;

import lombok.Data;

@Data
public class ProductVO {
    private int productId;         // 고유 상품 ID
    private String productName;           // 상품명
    private String productBarcode;        // 바코드 (NULL 가능)
    private int businessId;        // 사업자 ID
    private int categoryId;        // 카테고리 ID
    private String baseUnit;       // 기본 단위 (예: 개, 박스)
    private int setSize;           // 세트 크기 (예: 1박스 = 100개)
    private BigDecimal productPrice;      // 상품 기본 단가
    private Timestamp createdAt;   // 상품 등록 일자
    private Timestamp updatedAt;   // 상품 정보 수정 일자
    private String productQrCodePath;     // 상품 상세 QR 코드 저장 경로
    private String barcodePath;    // 바코드 이미지 저장 경로
    private String productDescription;    // 상품 설명
    private boolean isDeleted;     // 논리 삭제 여부 (true: 삭제됨, false: 활성)
    
    // 입출고 관련 QR 코드 정보
    private QRCodeDTO qrCode; // QRCodeDTO를 별도로 관리하여 입출고 관련 정보를 관리
}
