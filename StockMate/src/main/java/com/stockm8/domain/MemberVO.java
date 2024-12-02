package com.stockm8.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberVO {
    private int productId;
    private String name;
    private String barcode;
    private int categoryId;
    private String baseUnit;
    private int setSize;
    private BigDecimal price;
    private Timestamp createdAt; // 기본값은 DB에서 설정
    private Timestamp updatedAt; // 기본값은 DB에서 설정
    private int businessId;
    private String qrCodePath;
    private String barcodePath;
    private String description;
}
