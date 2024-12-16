package com.stockm8.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class FlashMessageInterceptor implements HandlerInterceptor {
    
	/**
     * 컨트롤러 실행 후 뷰가 렌더링되기 전에 호출되는 메서드.
     * FlashMap에 저장된 메시지를 뷰에 전달.
     *
     * @param request      HTTP 요청 객체
     * @param response     HTTP 응답 객체
     * @param handler      요청을 처리하는 핸들러(컨트롤러)
     * @param modelAndView 모델과 뷰 정보
     * @throws Exception 예외 발생 시
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // FlashMap에서 메시지 가져오기
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        
        if (flashMap != null && modelAndView != null) {
            String errorMessage = (String) flashMap.get("errorMessage");
            if (errorMessage != null) {
                modelAndView.addObject("errorMessage", errorMessage);
            }
            
            // successMessage가 있으면 모델에 추가
            String successMessage = (String) flashMap.get("successMessage");
            if (successMessage != null) {
                modelAndView.addObject("successMessage", successMessage);
            }
        }
    }

    /**
     * 요청 처리 전 호출되는 메서드.
     *
     * @param request  HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param handler  요청을 처리하는 핸들러(컨트롤러)
     * @return true: 요청 진행, false: 요청 중단
     * @throws Exception 예외 발생 시
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true; // 요청 진행
    }

    /**
     * 요청 완료 후 호출되는 메서드. (예외 처리 및 리소스 정리에 유용)
     *
     * @param request  HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param handler  요청을 처리한 핸들러(컨트롤러)
     * @param ex       요청 처리 중 발생한 예외
     * @throws Exception 예외 발생 시
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 현재는 별도 동작 없음 (필요하면 로직 추가 가능)
    }
    
}