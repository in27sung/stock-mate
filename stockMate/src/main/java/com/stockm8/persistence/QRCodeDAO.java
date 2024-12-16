package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.vo.QRCodeVO;

public interface QRCodeDAO {
	
    public void insertQRCode(QRCodeVO qrCode);
    
    public QRCodeVO selectQRCodeByProductId(int productId);
    
    // 비즈니스 ID에 속하는 상품들의 QR 코드 경로 가져오기
    List<QRCodeVO> selectQRCodePathsByBusinessId(int businessId) throws Exception;
}
