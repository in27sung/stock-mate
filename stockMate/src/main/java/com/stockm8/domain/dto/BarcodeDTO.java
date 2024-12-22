package com.stockm8.domain.dto;

import lombok.Data;

@Data
public class BarcodeDTO {
    private int productId;
    private String stockQrCodePath;  // 바코드 및 QR코드 경로 (테이블에 barcode_path로 매핑)
    private String stockQrCodeData;  // 바코드 데이터 (13자리 EAN-13 등)
}
