package com.stockm8.domain.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BusinessVO {

	private int businessId; 		 // 사업자 고유 ID
	private String businessNumber;   // 사업자등록번호
	private String companyName;  	 // 회사명
	private Timestamp createdAt;	 // 사업자 등록 날짜

}
