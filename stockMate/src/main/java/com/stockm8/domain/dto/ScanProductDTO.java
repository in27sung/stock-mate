package com.stockm8.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ScanProductDTO {
    @JsonProperty("productId")
    private int productId;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("businessId")
    private int businessId;

    @JsonProperty("baseUnit")
    private String baseUnit;

    @JsonProperty("setSize")
    private int setSize;
}