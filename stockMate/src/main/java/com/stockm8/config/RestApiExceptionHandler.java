package com.stockm8.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.stockm8.exceptions.QRCodeGenerationException;
import com.stockm8.exceptions.QRCodeNotFoundException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestApiExceptionHandler.class);

    /**
     * QR 코드 생성 예외 처리
     */
    @ExceptionHandler(QRCodeGenerationException.class)
    public ResponseEntity<Map<String, Object>> handleQRCodeGenerationException(QRCodeGenerationException ex) {
        logger.error("QR 코드 생성 실패:", ex);

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "QR 코드 생성 실패: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * QR 코드가 없는 경우 예외 처리
     */
    @ExceptionHandler(QRCodeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleQRCodeNotFoundException(QRCodeNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
    }

    /**
     * 기타 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "서버에서 오류가 발생했습니다: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 Internal Server Error
    }
}
