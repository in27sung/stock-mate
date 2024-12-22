package com.stockm8.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stockm8.domain.enums.UserRole;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	//http://localhost:8088/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home sweet home 모두 화이팅😁! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		return "main";
	}
	
	//http://localhost:8088/dashboard
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dash(HttpSession session, Locale locale, Model model) throws Exception {
		logger.info("😁Welcome dashboad😁! The client locale is {}.", locale);
		
        // 세션에서 userId 가져오기
		Long userId = (session != null) ? (Long) session.getAttribute("userId") : null;

        // userId로 사용자 정보 조회
        UserVO user = userService.getUserById(userId);
        logger.info("세션으로 들고온 유저정보: " + user);
        UserRole userRole = user.getUserRole();
        
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
	    model.addAttribute("userRole", userRole);

		return "dashboard";
	}
	
    // http://localhost:8088/qrScanner
	@RequestMapping(value = "/qrScanner", method = RequestMethod.GET)
	public String qrScanner(Locale locale, Model model) {
		logger.info("😁Welcome scanner😁! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "qrScanner";
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
		return "howtouse";
	}
	
	// 창고위치 - /location (GET)
		@RequestMapping(value = "/location", method = RequestMethod.GET)
		public String locationGET(Model model, HttpSession session) throws Exception {
			Long userId = (Long) session.getAttribute("userId");
//	                if (id == null) {
//	                    // 세션에 id가 없으면 에러 처리
//	                    return "redirect:/user/main";
//	                }
//	               UserVO resultVO = userService.getUser(userId);
//	               model.addAttribute("resultVO", resultVO);
			return "location";
		}
	
	
	// 상담하기 - /consultation (GET)
	@RequestMapping(value = "/consultation", method = RequestMethod.GET)
	public String consultationGET(Model model, HttpSession session) throws Exception {
		Long userId = (Long) session.getAttribute("id");

//                if (id == null) {
//                    // 세션에 id가 없으면 에러 처리
//                    return "redirect:/user/main";
//                }
//               UserVO resultVO = userService.getUser(userId, password );
//               model.addAttribute("resultVO", resultVO);
		return "consultation";
	}
	
	
	// 상담하기 email - POST
	@RequestMapping(value = "/", method = RequestMethod.POST)
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

	    // http://localhost:8088/로 리다이렉트
	    return "redirect:/";
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
		return "/intro";
	}
	
	// 회사소개 - /price (GET)
	@RequestMapping(value = "/price", method = RequestMethod.GET)
	public String priceGET(Model model, HttpSession session) throws Exception {
		Long userId = (Long) session.getAttribute("userId");
//                if (id == null) {
//                    // 세션에 id가 없으면 에러 처리
//                    return "redirect:/user/main";
//                }
//               UserVO resultVO = userService.getUser(userId);
//               model.addAttribute("resultVO", resultVO);
		return "/price";
	}
	
	
	// 약도 - /minimap (GET)
		@RequestMapping(value = "/minimap", method = RequestMethod.GET)
		public String minimapGET(Model model, HttpSession session) throws Exception {
			Long userId = (Long) session.getAttribute("userId");
//	                if (id == null) {
//	                    // 세션에 id가 없으면 에러 처리
//	                    return "redirect:/user/main";
//	                }
//	               UserVO resultVO = userService.getUser(userId);
//	               model.addAttribute("resultVO", resultVO);
			return "/minimap";
		}
		
}
	
