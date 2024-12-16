package com.stockm8.exceptions;

/**
 * QR 코드 생성 중 발생하는 예외를 처리하기 위한 사용자 정의 예외 클래스
 */
public class QRCodeGenerationException extends RuntimeException {

    // 기본 생성자
    public QRCodeGenerationException() {
        super("QR 코드 생성 중 오류가 발생했습니다.");
    }

    // 메시지를 받는 생성자
    public QRCodeGenerationException(String message) {
        super(message);
    }

    // 메시지와 원인을 받는 생성자
    public QRCodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    // 원인을 받는 생성자
    public QRCodeGenerationException(Throwable cause) {
        super(cause);
    }
}
