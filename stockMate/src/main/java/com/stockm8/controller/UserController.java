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
	// http://localhost:8088/user/main (o)
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

	// http://localhost:8088/user/login (GET)
	// 로그인 - 정보입력 / GET
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String userSgininGET(HttpServletRequest request, Model model) {
		logger.info("userSgininGET(HttpServletRequest request, Model model) 호출 ");
		logger.info("!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

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

			// 원래 요청 URL로 리다이렉트
//	        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
//	        
//	        if (redirectUrl != null) {
//	            session.removeAttribute("redirectAfterLogin"); // 세션에서 URL 삭제
//	            return "redirect:" + redirectUrl;
//	        }
			return "redirect:/dashboard";
		}

		// 로그인 실패 처리
		logger.warn("로그인 실패, 사용자 정보를 찾을 수 없습니다.");
		rttr.addFlashAttribute("errorMessage", "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
		return "redirect:/user/signin"; // 로그인 페이지 이동
	}

	// 메인페이지 - GET
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void mainGET(HttpServletRequest request, Model model) throws Exception {
		logger.info(" mainGET() 호출 ");

		// FlashMap에서 에러 메시지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			String errorMessage = (String) flashMap.get("errorMessage");
			if (errorMessage != null) {
				model.addAttribute("errorMessage", errorMessage);
			}
		}

		logger.info(" /user/main -> /user/main.jsp 연결 ");
	}

	// 대시보드 페이지 - GET
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public void dashGET() {

		logger.info(" dashGET() 호출 ");

		logger.info(" /user/main -> /user/dash.jsp 연결 ");
	}

	// 로그아웃 - GET
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String userLogoutGET(HttpSession session) throws Exception {
		logger.info(" userLogoutGET() 호출");

		// 로그아웃 처리(세션정보 초기화)
		session.invalidate();

		// 로그아웃 처리 후 페이지 이동

		return "redirect:/user/main";
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


	// 비밀번호 찾기 - get
	@RequestMapping(value = "/findPassword", method = RequestMethod.GET)
	public String findPasswordGet() {

		return "/user/findPassword";
	}

//	// 회원정보 수정 - GET
//	// (기존정보를 가져와서 보여주고, 수정할 정보를 입력)
//	@RequestMapping(value = "/editinfo1", method = RequestMethod.GET)
//	public void userUpdateGET(@SessionAttribute("userId") Long userId, Model model) throws Exception {
//		logger.info(" userUpdateGET() 호출 ");
//
//		// 사용자의 ID정보를 가져오기(세션)
//		logger.info("userId : " + userId);
//
//		// 서비스 -> DAO 회원정보 가져오는 동작 호출
//		UserVO resultVO = userService.getUser(userId);
//
//		// 연결된 뷰페이지에 출력
//		// => model 객체에 정보 저장
//		model.addAttribute("resultVO", resultVO);
//		// /user/update.jsp 뷰페이지 연결
//	}
//

	// 회원정보 수정
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

	
	// 상담하기 - /user/consultation (GET)
	@RequestMapping(value = "/consultation", method = RequestMethod.GET)
	public String consultationGET(Model model, HttpSession session) throws Exception {
		Long userId = (Long) session.getAttribute("id");

//                if (id == null) {
//                    // 세션에 id가 없으면 에러 처리
//                    return "redirect:/user/main";
//                }
//               UserVO resultVO = userService.getUser(userId, password );
//               model.addAttribute("resultVO", resultVO);
		return "/user/consultation";
	}

	
	

	@RequestMapping(value = "/sendConsultation", method = RequestMethod.POST)
	public String sendConsultation(
	        @RequestParam("company") String company,
	        @RequestParam("name") String name,
	        @RequestParam("contact") String contact,
	        @RequestParam("email") String email,
	        @RequestParam("inquiry") String inquiry,
	        RedirectAttributes rttr) {

	    final String fromEmail = "zzangmait@naver.com"; // 네이버 이메일 계정
	    final String password = "571TT3J3UMVY"; // 네이버 이메일 비밀번호

	    String toEmail = "zzangmait@naver.com"; // 수신 이메일 주소

	    // SMTP 설정
	    Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.naver.com");
	    props.put("mail.smtp.port", "465");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.ssl.enable", "true"); // SSL 활성화
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(fromEmail, password);
	        }
	    });

	    try {
	        // 이메일 내용 설정
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new javax.mail.internet.InternetAddress(fromEmail));
	        message.addRecipient(Message.RecipientType.TO, new javax.mail.internet.InternetAddress(toEmail));
	        message.setSubject("상담 신청 정보");

	        // HTML 형식 이메일 본문
	        String content = "<html>"
	                + "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0;'>"
	                + "<div style='width: 100%; max-width: 600px; margin: 20px auto; border: 2px solid #005bac; border-radius: 10px; padding: 15px;'>"
	                + "<h2 style='color: #005bac; text-align: center;'>상담 신청 정보</h2>"
	                + "<table style='width: 100%; border-collapse: collapse;'>"
	                + "  <tr style='background-color: #f2f9ff;'>"
	                + "    <th style='text-align: left; padding: 8px; border: 1px solid #005bac; color: #005bac;'>회사명</th>"
	                + "    <td style='padding: 8px; border: 1px solid #005bac;'>" + company + "</td>"
	                + "  </tr>"
	                + "  <tr>"
	                + "    <th style='text-align: left; padding: 8px; border: 1px solid #005bac; color: #005bac;'>이름</th>"
	                + "    <td style='padding: 8px; border: 1px solid #005bac;'>" + name + "</td>"
	                + "  </tr>"
	                + "  <tr style='background-color: #f2f9ff;'>"
	                + "    <th style='text-align: left; padding: 8px; border: 1px solid #005bac; color: #005bac;'>연락처</th>"
	                + "    <td style='padding: 8px; border: 1px solid #005bac;'>" + contact + "</td>"
	                + "  </tr>"
	                + "  <tr>"
	                + "    <th style='text-align: left; padding: 8px; border: 1px solid #005bac; color: #005bac;'>이메일</th>"
	                + "    <td style='padding: 8px; border: 1px solid #005bac;'>" + email + "</td>"
	                + "  </tr>"
	                + "  <tr style='background-color: #f2f9ff;'>"
	                + "    <th style='text-align: left; padding: 8px; border: 1px solid #005bac; color: #005bac;'>문의 내용</th>"
	                + "    <td style='padding: 8px; border: 1px solid #005bac;'>" + inquiry + "</td>"
	                + "  </tr>"
	                + "</table>"
	                + "<p style='text-align: center; margin-top: 16px; color: #555;'>상담 신청해주셔서 감사합니다.</p>"
	                + "</div>"
	                + "</body></html>";

	        // 이메일 본문을 HTML로 설정
	        message.setContent(content, "text/html; charset=UTF-8");

	        // 메일 전송
	        Transport.send(message);
	        rttr.addFlashAttribute("message", "상담 신청이 성공적으로 전송되었습니다.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        rttr.addFlashAttribute("error", "이메일 전송에 실패했습니다. 다시 시도해주세요.");
	    }

	    return "redirect:/user/consultation";
	}


	
	
	// 대시보드사용법 - /howtouse (GET)
	@RequestMapping(value = "/howtouse", method = RequestMethod.GET)
	public String howtouseGET(Model model, HttpSession session) throws Exception {
		Long userId = (Long) session.getAttribute("userId");
//                if (id == null) {
//                    // 세션에 id가 없으면 에러 처리
//                    return "redirect:/user/main";
//                }
//               UserVO resultVO = userService.getUser(userId);
//               model.addAttribute("resultVO", resultVO);
		return "/user/howtouse";
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

	// 회사소개 - /intro (GET)
	@RequestMapping(value = "/intro", method = RequestMethod.GET)
	public String introGET(Model model, HttpSession session) throws Exception {
		Long userId = (Long) session.getAttribute("userId");
//                if (id == null) {
//                    // 세션에 id가 없으면 에러 처리
//                    return "redirect:/user/main";
//                }
//               UserVO resultVO = userService.getUser(userId);
//               model.addAttribute("resultVO", resultVO);
		return "/user/intro";
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