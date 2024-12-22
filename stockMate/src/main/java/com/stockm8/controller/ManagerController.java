package com.stockm8.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.stockm8.domain.dto.PendingUserDTO;
import com.stockm8.domain.enums.UserRole;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.UserService;

@Controller
@RequestMapping(value = "/manager/*")
public class ManagerController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 매니저 메인 페이지 표시(GET) 
	 * 
	 * @throws Exception
	 * 
	 */
	// http://localhost:8088/admin/main
	@GetMapping("/main")
	public String managerMainGET(@SessionAttribute("userId") Long userId, Model model) throws Exception {
		logger.info("managerMainGET() 호출 - 페이지 접근 (userId: {})", userId);

		// userId로 사용자 정보 가져오기
		UserVO user = userService.getUserById(userId);

		if (user != null) {
			// 관리자 여부 확인 (MANAGER인 경우)
			boolean isManager = user.getUserRole() == UserRole.MANAGER;
			model.addAttribute("isManager", isManager); // JSP에 전달할 데이터
		}
		return "manager/main"; // 메인 페이지 반환
	}
	
	// http://localhost:8088/manager/approve
	@GetMapping("/approve")
	public String managerApproveGET(@SessionAttribute("userId") Long userId, Model model) throws Exception {
        logger.info("managerApproveGET() 호출 - 페이지 접근 (userId: {})", userId);
        
		// userId로 사용자 정보 가져오기
		UserVO user = userService.getUserById(userId);
	    int businessId = user.getBusinessId();
	    
        // 조건에 맞는 사용자와 사업자 정보 가져오기
        List<PendingUserDTO> pendingUsers = userService.getStaffByBusinessId(businessId);

        // JSP로 전달
        model.addAttribute("pendingUsers", pendingUsers);

        return "admin/approve"; // JSP 페이지 반환
    }

}
