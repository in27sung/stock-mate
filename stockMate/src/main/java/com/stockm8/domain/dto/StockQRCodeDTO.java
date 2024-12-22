package com.stockm8.domain.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class StockQRCodeDTO {
    private int stockId;
    private int productId;
    private String productBarcode;
    private int warehouseId;
    private String warehouseName;
    private int totalQuantity;
    private int availableStock;
    private String stockQrCodePath;
    private String stockQrCodeData;
    private Timestamp updatedAt;
}
