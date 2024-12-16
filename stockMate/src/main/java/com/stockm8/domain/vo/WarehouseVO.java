package com.stockm8.domain.vo;

import java.sql.Timestamp;

import com.stockm8.domain.enums.WarehouseStatus;

import lombok.Data;

@Data
public class WarehouseVO {
	private int warehouseId; 			 // 창고 고유 ID
	private String warehouseName;  		 // 창고 이름
	private String warehouseRegion;               // 창고 지역 정보
	private String warehouseLocation;	   	 	 // 창고 위치
	private int businessId; 			 // 사업자 ID
	private Long managerId; 			 // 창고 생성자 ID
	private Timestamp createdAt;		 // 창고 등록 날짜
	private Timestamp updatedAt;		 // 창고 수정 날짜
	private int warehouseCapacity;                // 창고 저장 용량
	private String warehouseDescription; // 창고에 대한 상세 설명
	private WarehouseStatus warehouseStatus;               // 창고 상태 ('active', 'inactive')
    private boolean isDeleted;     		 // 논리 삭제 여부 (true: 삭제됨, false: 활성)
    
}
