package com.stockm8.domain.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CategoryVO {
	private int categoryId;        // 카테고리 고유 ID
    private Integer parentId;      // 상위 카테고리 ID (NULL이면 대분류)
    private int businessId;        // 사업자 ID
    private String categoryName;   // 카테고리 이름
    private int level;             // 카테고리 계층 수준 (1=대분류, 2=소분류)
    private Timestamp createdAt;   // 카테고리 생성 일자
}
