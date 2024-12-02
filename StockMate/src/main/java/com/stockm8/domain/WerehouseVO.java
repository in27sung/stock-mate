package com.stockm8.domain;

import java.sql.Timestamp;

import lombok.Data;

public class WerehouseVO {
	
	@Data
	public class WarehouseVO {
	    private int warehouseId;       // 창고 고유 ID
	    private String warehouseName;  // 창고 이름
	    private String location;       // 창고 위치
	    private int businessId;        // 사업자 ID
	    private int managerId;         // 창고 담당자 ID
	    private Timestamp createdAt;   // 창고 등록 날짜
	}
}
