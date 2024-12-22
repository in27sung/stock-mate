package com.stockm8.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.UserService;

import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Session;
import java.util.Properties;
import javax.mail.PasswordAuthentication;

@Controller
@RequestMapping(value = "/user/*")
// *.me 처럼 /user/~ 시작하는 모든주소를 처리하겠다.
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	// userDAO 객체 root-context.xml주입
	// @Inject
	// private userDAO mdao;

	@Autowired
	private UserService userService;

	// http://localhost:8088/user/signup (o)
	// http://localhost:8088/user/signin (o)
	// http://localhost:8088/ (o)
	// http://localhost:8088/user/info1 (o)
	// http://localhost:8088/user/info2 (o)
	// http://localhost:8088/dashboard (o)
	// http://localhost:8088/user/info1 (o)
	// http://localhost:8088/user/info2 (o)
	// http://localhost:8088/user/editinfo1 (o)
	// http://localhost:8088/user/editinfo2 (o)
	// http://localhost:8088/user/consultation (o)
	// http://localhost:8088/user/changepassword1 (o)

	// 회원가입 - 정보입력 / GET 방식
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public void userSignUpGET() throws Exception {
		logger.info(" /user/join -> userJoinGET() 실행");
		logger.info(" 주소에 맞는 view페이지 매핑(보여주기)");
		// return "/user/join"; // /view/user/join.jsp 연결

	}

	// ./user/join?userid=qqqq&userpw=1111...
	// 회원가입 - 정보처리 / POST 방식
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String userSignUpPOST(/* @ModelAttribute */ UserVO user) throws Exception {
		logger.info("/user/signup -> userSignUpPOST(UserVO uservo)실행 ");
		logger.info("Role received: " + user.getUserRole());

		// 전달정보 저장
		logger.info("vo :" + user);

		// userDAO객체가 필요 => 주입
		// DB에 정보를 전달 - 회원가입동작 실행
		// mdao.userJoin(vo); // => 잘못됨
		// 서비스 -> DAO 호출
		userService.userJoin(user);

		// 로그인 페이지로 이동
		return "redirect:/user/signin";
	}

	// http://localhost:8088/user/signin (GET)
	// 로그인 - 정보입력 / GET
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String userSgininGET(HttpServletRequest request, Model model) {
		logger.info("userSgininGET() 호출 ");

		// FlashMap에서 에러 메시지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			String errorMessage = (String) flashMap.get("errorMessage");
			if (errorMessage != null) {
				model.addAttribute("errorMessage", errorMessage);
			}
		}
		return "/user/signin";
	}

	// 로그인 - 정보처리 / POST
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String userLoginPOST(UserVO user, RedirectAttributes rttr, HttpSession session) throws Exception {
		logger.info("userLoginPOST() 호출");

		// 입력받은 로그인 정보 출력
		logger.debug("입력된 로그인 정보: {}", user);

		// 서비스 계층에서 로그인 체크 호출
		UserVO resultVO = userService.userLogin(user);

		// 로그인 성공 처리
		if (resultVO != null) {
			logger.info("로그인 성공, 사용자 ID: {}", resultVO.getUserId());

		// 세션에 사용자 ID 저장
		session.setAttribute("userId", resultVO.getUserId());
        logger.debug("세션 저장 완료 - Session ID: {}, User ID: {}", session.getId(), session.getAttribute("userId"));

		// 원래 요청 URL로 리다이렉트
        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
        
        if (redirectUrl != null) {
            session.removeAttribute("redirectAfterLogin"); // 세션에서 URL 삭제
            return "redirect:" + redirectUrl;
        }
			return "redirect:/dashboard";
		}
		// 로그인 실패 처리
		logger.warn("로그인 실패, 사용자 정보를 찾을 수 없습니다.");
		rttr.addFlashAttribute("errorMessage", "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
		return "redirect:/user/signin"; // 로그인 페이지 이동
	}

	// 로그아웃 - GET
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String userLogoutGET(HttpSession session) throws Exception {
		logger.info(" userLogoutGET() 호출");

		// 로그아웃 처리(세션정보 초기화)
		session.invalidate();

		// 로그아웃 처리 후 페이지 이동

		return "redirect:/user/signin";
	}


	// 회원정보 수정
	@RequestMapping(value = "/editinfo1", method = RequestMethod.GET)
	public String userEditInfo1GET(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			// 세션에 사용자 ID가 없으면 대시보드로 리다이렉트
			return "redirect:/dashboard";
			
			
			
		}
		return "/user/editinfo1"; // 비밀번호 입력 페이지로 이동
	}
	


	@RequestMapping(value = "/editinfo1", method = RequestMethod.POST)
	public String userEditInfo2POST(@RequestParam("password") String password, HttpSession session, RedirectAttributes rttr) {
	    Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        return "redirect:/dashboard";
	    }

	    try {
	        UserVO userVO = userService.getUser(userId, password);

	        if (userVO == null) {
	            rttr.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
	            return "redirect:/user/editinfo1";
	        }

	        // RedirectAttributes에 사용자 정보 추가
	        rttr.addFlashAttribute("userVO", userVO);
	        return "redirect:/user/editinfo2";

	    } catch (Exception e) {
	        rttr.addFlashAttribute("errorMessage", "시스템 오류가 발생했습니다.");
	        return "redirect:/user/editinfo1";
	    }
	}


	
	@RequestMapping(value = "/editinfo2", method = RequestMethod.GET)
	public String userEditInfo2GET(HttpSession session, Model model) {
	    if (!model.containsAttribute("userVO")) {
	        return "redirect:/dashboard"; // 데이터가 없으면 대시보드로 리다이렉트
	    }

	    return "/user/editinfo2";
	}

	
	@RequestMapping(value = "/updateInfo2", method = RequestMethod.POST)
	public void userUpdatePOST(@ModelAttribute UserVO user, HttpSession session, HttpServletResponse response) throws Exception {
	    logger.info("userUpdatePOST() 실행");

	    Long userId = (Long) session.getAttribute("userId");
	    if (userId == null) {
	        response.sendRedirect("/dashboard");
	        return;
	    }

	    user.setUserId(userId); // userId 설정

	    try {
	    	System.out.println(user);
	        userService.updateUser(user); // 정보 업데이트
	        logger.info("회원정보 수정 성공");

	        // JavaScript로 알림창 띄우고 대시보드로 이동
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('변경완료'); location.href='/dashboard';</script>");
	        out.flush();
	    } catch (Exception e) {
	        logger.error("회원정보 수정 실패", e);

	        // 오류 발생 시 오류 메시지와 함께 돌아가기
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('오류가 발생했습니다. 다시 시도해주세요.'); history.back();</script>");
	        out.flush();
	    }
	}



	// 비밀번호 변경
	@RequestMapping(value = "/changepassword1", method = RequestMethod.GET)
	public String changepassword1GET(HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			// 세션에 사용자 ID가 없으면 대시보드로 리다이렉트
			return "redirect:/dashboard";
		}
		return "/user/changepassword1"; // 비밀번호 입력 페이지로 이동
	}
	
	
	@RequestMapping(value = "/changepassword1", method = RequestMethod.POST)
	public String changepassword2POST(@RequestParam("password") String password, RedirectAttributes rttr, HttpSession session) {
	    Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        return "redirect:/dashboard"; // 세션이 없는 경우 대시보드로 이동
	    }

	    try {
	        // 사용자 정보 조회 및 비밀번호 확인
	        UserVO userVO = userService.getUser(userId, password);

	        if (userVO == null) {
	            rttr.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
	            return "redirect:/user/changepassword1"; // 비밀번호 불일치 시 다시 이동
	        }

	        // 비밀번호를 모델에 추가 (FlashAttributes 사용)
	        rttr.addFlashAttribute("userVO", userVO);
	        rttr.addFlashAttribute("currentPassword", password); // 현재 비밀번호 추가
	        return "redirect:/user/changepassword2";

	    } catch (Exception e) {
	        logger.error("비밀번호 확인 중 오류 발생: ", e);
	        rttr.addFlashAttribute("errorMessage", "시스템 오류가 발생했습니다.");
	        return "redirect:/user/changepassword1";
	    }
	}
	
	
	@RequestMapping(value = "/changepassword2", method = RequestMethod.GET)
	public String changepassword2GET(@ModelAttribute("currentPassword") String currentPassword, Model model) {
	    if (currentPassword == null || currentPassword.isEmpty()) {
	        return "redirect:/user/changepassword1"; // 비밀번호가 없는 경우 다시 이동
	    }

	    model.addAttribute("currentPassword", currentPassword);
	    return "/user/changepassword2"; // JSP 페이지 반환
	}
	
	@RequestMapping(value = "/changepassword2", method = RequestMethod.POST)
	public void changePasswordPOST(
	        @RequestParam("newPassword") String newPassword,
	        @RequestParam("confirmPassword") String confirmPassword,
	        HttpSession session, 
	        HttpServletResponse response) throws Exception {
	    
	    Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        response.sendRedirect("/dashboard"); // 세션 없으면 대시보드로 이동
	        return;
	    }

	    // 비밀번호 일치 여부 확인
	    if (!newPassword.equals(confirmPassword)) {
	        // 비밀번호 불일치 시 오류 메시지와 함께 머무르기
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요.'); history.back();</script>");
	        out.flush();
	        return;
	    }

	    // 비밀번호 업데이트
	    try {
	        userService.updatePassword(userId, newPassword); // 비밀번호 변경
	        logger.info("비밀번호 변경 성공: userId = " + userId);

	        // 성공 메시지와 함께 대시보드로 이동
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('정보 변경 완료'); location.href='/dashboard';</script>");
	        out.flush();
	    } catch (Exception e) {
	        logger.error("비밀번호 변경 실패: ", e);

	        // 오류 발생 시 메시지와 함께 이전 페이지로 이동
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('시스템 오류가 발생했습니다. 다시 시도해주세요.'); history.back();</script>");
	        out.flush();
	    }
	}




	// 비밀번호 찾기 - GET
	//
    @RequestMapping(value = "/findpassword", method = RequestMethod.GET)
    public String findPasswordGet() {
        return "/user/findpassword";  // 비밀번호 찾기 페이지로 이동
    }

    // 비밀번호 찾기 - POST 
    @RequestMapping(value = "/findpassword", method = RequestMethod.POST)
    public String findpasswordPost(@RequestParam("email") String email,
                                    @RequestParam("name") String name,
                                    Model model) {
    	 try {
    		 System.out.println(email);
    		 System.out.println(name);
    	        // 이메일과 이름으로 비밀번호 찾기
    	        String password = userService.findPassword(email, name);  // 비밀번호를 String으로 받음
    	       System.out.println(password);
    	        if (password != null) {
    	            // 비밀번호가 일치하면 얼럿창을 통해 비밀번호를 사용자에게 전달
    	            model.addAttribute("password", password);  // 비밀번호를 model에 추가
    	            model.addAttribute("alertMessage", "입력한 정보에 해당하는 비밀번호는: " + password);
    	            return "/user/showpassword";  // 비밀번호를 보여주는 페이지로 이동
    	        } else {
    	            // 비밀번호가 일치하지 않으면 오류 메시지 추가
    	            logger.info("비밀번호 찾기 실패: 이메일 또는 이름이 일치하지 않음"); // 실패 로그
    	            model.addAttribute("errorMessage", "이메일 또는 이름이 일치하지 않습니다.");
    	            return "/user/findpassword";  // 비밀번호 찾기 페이지로 다시 이동
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        logger.error("비밀번호 찾기 처리 중 오류 발생", e);  // 예외 로그 추가
    	        model.addAttribute("errorMessage", "비밀번호 찾기 처리 중 오류가 발생했습니다.");
    	        return "/user/findpassword";  // 오류 발생 시 비밀번호 찾기 페이지로 돌아감
    	    }
    	}


	
	
	// 대시보드사용법 - /howtouse2 (GET)
	@RequestMapping(value = "/howtouse2", method = RequestMethod.GET)
	public String howtouseGET2(Model model, HttpSession session) throws Exception {
		Long userId = (Long) session.getAttribute("userId");
//                if (id == null) {
//                    // 세션에 id가 없으면 에러 처리
//                    return "redirect:/user/main";
//                }
//               UserVO resultVO = userService.getUser(userId);
//               model.addAttribute("resultVO", resultVO);
		return "/user/howtouse2";
	}



	// 회원정보 삭제 - 비밀번호 입력 (GET)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String userDeleteGET() throws Exception {
		logger.info("userDeleteGET() 실행 ");
		logger.info(" /user/delete.jsp 페이지 연결");

		return "/user/delete";
	}

	// 회원정보 삭제 - 아이디/비밀번호 확인후 정보 삭제 (POST)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String userDeletePOST(UserVO user, HttpSession session, @RequestParam("password") String password)
			throws Exception {
		logger.info(" userDeletePOST() 실행 ");

		// 전달된 파라메터(id,pw)를 저장
		logger.info(" vo : " + user);

		// 서비스 -> DAO 호출 (정보 삭제)
		int result = userService.deleteUser(user);

		if (result == 0) {
			// 삭제 실패
			logger.info(" 정보 삭제 실패!! ");
			return "redirect:/user/delete";
		}

		// 삭제 성공
		logger.info(" 정보 삭제 성공! ");
		// 삭제 후 정보초기화
		session.invalidate();

		return "redirect:/user/main";
	}

	// 회원목록 조회 - 회원정보를 리스트 형태로 가져오기 (GET)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String userListGET(Model model) throws Exception {
		logger.info(" userListGET() 호출 ");

		// 서비스 -> DAO (회원 리스트 가져오기 동작)
		List<UserVO> userList = userService.userList();
		logger.info(" 회원수 : " + userList.size());

		// 뷰페이지에 정보를 전달(model객체)
		model.addAttribute("userList", userList);

		// /user/list.jsp 페이지
		return "/user/list";
	}

}// Controller