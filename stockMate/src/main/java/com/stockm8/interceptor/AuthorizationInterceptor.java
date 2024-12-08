package com.stockm8.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.stockm8.domain.enums.Role;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.UserService;

/**
 * AuthorizationInterceptor: 
 * 모든 요청에서 사용자 인증 및 권한 확인을 처리하는 인터셉터. 
 * 사용자의 세션 상태와 DB 정보를 검증하여 적절한 페이지로 리다이렉트합니다.
 */

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
	
	@Autowired
	private UserService userService; // 유저 정보를 가져오기 위한 서비스 클래스

	/**
	 * 요청 전 처리 메서드. - 사용자의 로그인 여부 확인 - 삭제된 계정 여부 확인
	 *
	 * @param request  현재 HTTP 요청 객체
	 * @param response 현재 HTTP 응답 객체
	 * @param handler  현재 요청을 처리하는 핸들러 (컨트롤러)
	 * @return 요청을 계속 처리할지 여부 (true: 컨트롤러로 전달, false: 요청 중단)
	 * @throws Exception 예외가 발생한 경우 처리
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("AuthorizationInterceptor: 요청 URI - {}", request.getRequestURI());

		// 세션 확인 및 사용자 ID 가져오기
		HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
		Long userId = (session != null) ? (Long) session.getAttribute("userId") : null;
                
        // 1. 세션에서 사용자 ID 확인
        if (userId == null) {
            logger.warn("세션에 유저 ID가 없음. 로그인 페이지로 이동.");
            return sendErrorMessage(request, response, "세션이 만료되었습니다. 다시 로그인해주세요.", "/user/login");
        }
        
        // 2. DB에서 사용자 정보 조회
        UserVO user = userService.getUserById(userId);
        
        if (user == null) {
            logger.warn("해당 유저({}) 정보를 찾을 수 없음. 회원가입 페이지로 이동.", userId);
            return sendErrorMessage(request, response, "유저 정보를 찾을 수 없습니다. 회원가입을 진행해주세요.", "/user/signup");
        }
        
        // 3. 삭제된 계정 확인
        if (userService.getIsDeleted(userId) == 1) {
            logger.warn("삭제된 유저({}). 로그인 페이지로 이동.", userId);
            return sendErrorMessage(request, response, "삭제된 계정입니다. 관리자에게 문의해주세요.", "/user/login");
        }
		
        // 4. 권한 및 회사 정보 유효성 검사
        if (!isValidUser(request, response, user)) {
            return false; // 유효성 검사 실패 시 요청 중단
        }
        logger.info("(회사 ID: {})", user.getBusinessId());

        return true; // 유효성 검사 통과
	}

	 /**
     * Flash 메시지를 설정하고 리다이렉트합니다.
     *
     * @param request     요청 객체
     * @param response    응답 객체
     * @param message     사용자에게 표시할 메시지
     * @param redirectUrl 리다이렉트할 URL
     * @return false (요청 중단)
     * @throws Exception 예외 발생 시
     */
	private boolean sendErrorMessage(HttpServletRequest request, HttpServletResponse response, String message, String redirectUrl)
	        throws Exception {
	    // HttpSession에 에러 메시지 저장
	    HttpSession session = request.getSession();
	    session.setAttribute("errorMessage", message);

	    // 로그 출력
	    logger.info("HttpSession에 저장된 에러 메시지: {}", message);
	    logger.info("리다이렉트 대상 URL: {}", redirectUrl);

	    // 리다이렉트 수행
	    response.sendRedirect(redirectUrl);
	    return false; // Interceptor 처리 중단
	}
    
    /**
     * 유저 권한 및 회사 정보 검증
     *
     * @param request  요청 객체
     * @param response 응답 객체
     * @param user     유저 정보 객체
     * @return true (검증 통과), false (검증 실패)
     * @throws Exception 예외 발생 시
     */
    private boolean isValidUser(HttpServletRequest request, HttpServletResponse response, UserVO user) throws Exception {
        // 권한 확인
        List<Role> allowedRoles = Arrays.asList(Role.MANAGER, Role.ADMIN);
        if (user.getRole() == null || !allowedRoles.contains(user.getRole())) {
            logger.warn("권한이 없는 유저입니다. 대시보드로 리다이렉트합니다. (유저 ID: {}, 역할: {})", user.getUserId(), user.getRole());
            return sendErrorMessage(request, response, "접근 권한이 없습니다.", "/dashboard");
        }
        
        // 회사 정보 확인
        if (user.getBusinessId() == null) {
            logger.warn("사용자의 회사 정보가 없습니다. 회사 등록 페이지로 리다이렉트합니다. (회사 ID: {})", user.getBusinessId());
            return sendErrorMessage(request, response, "회사 정보가 없습니다. 회사를 등록해주세요.", "/company/register");
        }

        return true; // 모든 검증 통과
    }

	/**
	 * 컨트롤러 실행 후 호출되는 메서드. - 뷰 렌더링 전에 추가 작업을 수행할 수 있습니다. - 현재 코드에서는 추가 작업 없음.
	 *
	 * @param request      현재 HTTP 요청 객체
	 * @param response     현재 HTTP 응답 객체
	 * @param handler      현재 요청을 처리하는 핸들러 (컨트롤러)
	 * @param modelAndView 컨트롤러가 반환하는 뷰 정보
	 * @throws Exception 예외가 발생한 경우 처리
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 현재는 사용하지 않음 (필요하면 로직 추가 가능)
	}

	/**
	 * 요청 완료 후 호출되는 메서드. - 예외 로깅, 리소스 정리 등을 수행할 수 있습니다.
	 *
	 * @param request  현재 HTTP 요청 객체
	 * @param response 현재 HTTP 응답 객체
	 * @param handler  현재 요청을 처리한 핸들러 (컨트롤러)
	 * @param ex       요청 처리 중 발생한 예외 (없으면 null)
	 * @throws Exception 예외가 발생한 경우 처리
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 현재는 사용하지 않음 (필요하면 로직 추가 가능)
	}
}