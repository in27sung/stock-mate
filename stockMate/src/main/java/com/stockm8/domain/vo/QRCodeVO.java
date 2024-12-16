package com.stockm8.domain.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class QRCodeVO {
    private int qrCodeId;
    private int productId;
    private String qrCodeData;
    private String qrCodePath;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}