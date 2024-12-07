package com.stockm8.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stockm8.service.WarehouseService;

@Controller
@RequestMapping(value = "/warehouse/*") // 공통 URI 주소
public class WarehouseController {

	private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);
	
	@Inject
	private WarehouseService wService;
	
	// http://localhost:8088/warehouse/create
	// 창고 등록하기
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public void registGET() throws Exception {
		logger.info("/warehouse/create 호출");
		logger.info("연결된 뷰페이지(/board/create.jsp) 이동");
	}
	
	// 창고 등록처리 
	@PostMapping("/create")
	public String registPOST() throws Exception {
		logger.info("createPOST() 호출");
		return null;
	}
}
