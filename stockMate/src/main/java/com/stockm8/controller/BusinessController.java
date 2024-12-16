package com.stockm8.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stockm8.domain.vo.BusinessVO;
import com.stockm8.service.BusinessService;
import com.stockm8.service.UserService;

@Controller
@RequestMapping("/business")
public class BusinessController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);

	@Autowired
	private BusinessService businessService;

	@Autowired
	private UserService userService;

	// http://localhost:8088/business/register
	// 회사 등록 페이지
	@GetMapping("/register")
	public String registBusinessGET() throws Exception {
		logger.info("registBusinessGET() 호출");

		return "business/register"; // 회사 등록 폼 페이지로 이동
	}

	// 비즈니스 등록 처리
	@PostMapping("/register")
	public String registerBusinessPOST(BusinessVO business, HttpServletRequest request, Model model) throws Exception {
		logger.info("registBusinessPOST() 호출");

		// 세션에서 사용자 ID 가져오기
		HttpSession session = request.getSession(false);
		Long userId = (session != null) ? (Long) session.getAttribute("userId") : null;

		// 비즈니스 등록 및 사용자 businessId 업데이트 처리
		businessService.registerBusinessWithUserUpdate(business, userId);

	    // 성공 메시지를 FlashAttributes에 추가
		model.addAttribute("successMessage", "비즈니스가 성공적으로 등록되었습니다.");
		return "redirect:/dashboard"; // 대시보드로 리다이렉트
	}
	
	/**
	 * 비즈니스 인증 페이지 GET 요청 처리 
	 */
	@GetMapping("/verify")
	public String verifyBusinessGET(Model model, HttpServletRequest request) throws Exception {
		logger.info("verifyBusinessGET");
		
		return "business/verify";
	}
	
	/**
	 * 비즈니스 인증 POST 요청 처리
	 */
	@PostMapping("/verify")
	public String verifyPOST(
			BusinessVO business,
			HttpServletRequest request,
			Model model) throws Exception{
		
		logger.info("verifyPOST() 호출");
		
		// 세션에서 사용자 ID 가져오기
		HttpSession session = request.getSession(false);
		Long userId = (session != null) ? (Long) session.getAttribute("userId") : null;
		
	    // 비즈니스 정보 확인
	    BusinessVO foundBusiness = businessService.getBusinessByNumberAndName(business.getBusinessNumber(), business.getBusinessName());
	    
	    logger.info("사용자가 입력한 사업자 등록 번호: {}", business.getBusinessNumber());
	    logger.info("사용자가 입력한 회사 이름: {}", business.getBusinessName());
	    
	    if (foundBusiness == null || foundBusiness.getBusinessId() == null) {
			logger.warn("입력된 비즈니스 정보가 존재하지 않습니다.");
			model.addAttribute("errorMessage", "입력된 비즈니스 정보가 존재하지 않습니다. 다시 확인해주세요.");
			return "business/verify";
		}
		
		// 유저 테이블에 businessId 업데이트 
	    int businessId = foundBusiness.getBusinessId(); // 여기서 businessId 값을 다시 확인
	    logger.info("조회된 비즈니스 ID: {}", businessId);
	    
		userService.updateUserBusinessId(userId, businessId);
		
		logger.info("비즈니스 인증 성공: userId={}, businessId={}", userId, business.getBusinessId());
		model.addAttribute("successMessage", "비즈니스 인증이 성공적으로 완료되었습니다.");
	
		return "redirect:/dashboard";
	}
	
}
