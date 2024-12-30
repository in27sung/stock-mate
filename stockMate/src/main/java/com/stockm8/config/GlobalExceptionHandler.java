package com.stockm8.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stockm8.exceptions.BusinessRegistrationException;
import com.stockm8.exceptions.QRCodeGenerationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 공통 예외 처리 (뷰 기반)
     */
    @ExceptionHandler(Exception.class)
    public String handleException(
            Exception e,
            HttpServletRequest request,
            RedirectAttributes rttr) {

        // 요청 URL 로그 기록
        logger.error("예외 발생 - URL: {}", request.getRequestURL());
        logger.error("예외 상세:", e);

        // 에러 메시지 설정
        rttr.addFlashAttribute("errorMessage", "서버 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");

        // 리다이렉트 경로 결정
        String referer = request.getHeader("Referer"); // 이전 페이지로 이동
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }

    /**
     * 비즈니스 등록 예외 처리
     */
    @ExceptionHandler(BusinessRegistrationException.class)
    public String handleBusinessRegistrationException(
            BusinessRegistrationException e,
            HttpServletRequest request,
            RedirectAttributes rttr) {

        logger.error("비즈니스 등록 예외 발생 - URL: {}", request.getRequestURL(), e);

        // 에러 메시지 설정
        rttr.addFlashAttribute("errorMessage", "비즈니스 등록 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");

        // 리다이렉트 경로 결정
        String referer = request.getHeader("Referer"); // 이전 페이지로 이동
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }

    /**
     * QR 코드 생성 중 발생하는 예외 처리
     */
    @ExceptionHandler(QRCodeGenerationException.class)
    public String handleQRCodeGenerationException(
            QRCodeGenerationException ex,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        // 예외 메시지 로그 출력
        logger.error("QR 코드 생성 실패:", ex);

        // 에러 메시지를 플래시 메시지로 전달
        redirectAttributes.addFlashAttribute("errorMessage", "QR 코드 생성 실패: " + ex.getMessage());

        // 리다이렉트 경로 결정
        String referer = request.getHeader("Referer"); // 이전 페이지로 이동
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }

    /**
     * 권한 예외 처리
     */
    @ExceptionHandler(SecurityException.class)
    public String handleSecurityException(
            SecurityException ex,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        logger.error("권한 부족 - URL: {}", request.getRequestURL(), ex);

        redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
        
        // 리다이렉트 경로 결정
        String referer = request.getHeader("Referer"); // 이전 페이지로 이동
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }

    /**
     * 잘못된 요청 예외 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        logger.error("잘못된 요청 - URL: {}", request.getRequestURL(), ex);

        redirectAttributes.addFlashAttribute("errorMessage", "잘못된 요청입니다: " + ex.getMessage());
        
        // 리다이렉트 경로 결정
        String referer = request.getHeader("Referer"); // 이전 페이지로 이동
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }
    
    // 404 예외 핸들러
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundException(
            NoHandlerFoundException ex,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        logger.error("404 Not Found - URL: {}", request.getRequestURL(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", "요청하신 페이지를 찾을 수 없습니다.");
        
        // 이전 페이지로 리다이렉트 (없다면 대시보드)
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }
    
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(
            NullPointerException ex,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        logger.error("Null Pointer Exception - URL: {}", request.getRequestURL(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", "요청 처리 중 오류가 발생했습니다. (NPE)");
        
        // 리다이렉트 경로 결정
        String referer = request.getHeader("Referer"); // 이전 페이지로 이동
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        logger.error("데이터 무결성 위반 - URL: {}", request.getRequestURL(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", "데이터 무결성 위반 오류가 발생했습니다.");
        
        // 리다이렉트 경로 결정
        String referer = request.getHeader("Referer"); // 이전 페이지로 이동
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(
            MaxUploadSizeExceededException ex,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        logger.error("파일 업로드 크기 초과 - URL: {}", request.getRequestURL(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", "파일 크기가 너무 큽니다. 최대 업로드 크기를 초과했습니다.");
        
        // 리다이렉트 경로 결정
        String referer = request.getHeader("Referer"); // 이전 페이지로 이동
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }
}
