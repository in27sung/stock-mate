package com.stockm8.service;

import com.stockm8.domain.vo.QRCodeVO;

public interface QRCodeService {
	
    // QR 코드 생성의 전체 흐름
	public void generateQRCode(int productId) throws Exception;
    
	// JSON QR 코드 경로를 가져
    public QRCodeVO getQRCodeByProductId(int productId) throws Exception; // 추가된 메서드

    // QR 코드 데이터 생성
//    public String createQRContent(int productId) throws Exception;
    
    // QR 코드 경로 생성
//    public String createQRCodePath(ProductVO product, boolean isJsonQRCode) throws Exception;
    
    // QR 코드 이미지 생성
//    public void generateQRCodeImage(String qrContent, String qrCodePath) throws Exception;
}
