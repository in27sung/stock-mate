package com.stockm8.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.stockm8.domain.dto.PendingUserDTO;
import com.stockm8.domain.dto.UpdateUserStatusDTO;
import com.stockm8.domain.enums.UserRole;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.OrderService;
import com.stockm8.service.UserService;

@Controller
@RequestMapping(value = "/admin/*")
public class AdminController {

	// 현재 로그인한 사용자 정보 가져오기(인터셉터에서 정의됨)
	private UserVO getCurrentUser(HttpServletRequest request) {
		return (UserVO) request.getAttribute("currentUser");
	}

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Inject
	private OrderService orderService;
	@Inject
	private UserService userService;

	/**
	 * 관리자 메인 페이지 표시(GET) 
	 * 
	 * @throws Exception
	 * 
	 */
	// http://localhost:8088/admin/main
	@GetMapping("/main")
	public String adminMainGET(@SessionAttribute("userId") Long userId, Model model) throws Exception {
		logger.info("adminMainGET() 호출 - 페이지 접근 (userId: {})", userId);

		// userId로 사용자 정보 가져오기
		UserVO user = userService.getUserById(userId);

		if (user != null) {
			// 관리자 여부 확인 (ADMIN인 경우)
			boolean isAdmin = user.getUserRole() == UserRole.ADMIN;
			model.addAttribute("isAdmin", isAdmin); // JSP에 전달할 데이터
		}
		return "admin/main"; // 메인 페이지 반환
	}
	
	// http://localhost:8088/admin/approve
	@GetMapping("/approve")
	public String adminApproveGET(@SessionAttribute("userId") Long userId, Model model) throws Exception {
        logger.info("adminApproveGET() 호출 - 페이지 접근 (userId: {})", userId);

        // 조건에 맞는 사용자와 사업자 정보 가져오기
        List<PendingUserDTO> pendingUsers = userService.getPendingUsersWithBusiness();

        // JSP로 전달
        model.addAttribute("pendingUsers", pendingUsers);

        return "admin/approve"; // JSP 페이지 반환
    }

	/**
	 * 관리자 회원목록표시(GET) 
	 * 
	 */
    // http://localhost:8088/admin/userList
	@RequestMapping(value = "/adminList", method = RequestMethod.GET)
	public String adminListGET(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("adminListGET() 호출");

		UserVO currentUser = getCurrentUser(request);
		int businessId = currentUser.getBusinessId();

		return "admin/userList";
	}

} // AdminController