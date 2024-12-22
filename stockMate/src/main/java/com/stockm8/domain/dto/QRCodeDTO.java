package com.stockm8.domain.dto;

import lombok.Data;

@Data
public class QRCodeDTO {
    private String productBarcode;        // 바코드 (NULL 가능)
    private int productId;
    private String qrCodeData;
    private String qrCodePath;
    // Getters and Setters
}