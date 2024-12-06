//package com.stockm8.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//// Advice : 보조기능(예외처리)을 구현한 객체
//// => 프로젝트에서 발생한 모든 예외를 처리하는 객체
//// => 컨트롤러가 던진 예외를 받아서 처리하는 객체
//@ControllerAdvice
//public class CommonsExceptionAdvice {
//	
//	private static final Logger logger = LoggerFactory.getLogger(CommonsExceptionAdvice.class);
//	
//	// 예외 처리
//	@ExceptionHandler(Exception.class) // 모든 예외객체 처리
//	public String commons(Exception e) {
//		logger.info("e : " + e);
//		
//		return "/commonErr"; // 연결된 뷰페이지를 설정
//	}
//
//}
