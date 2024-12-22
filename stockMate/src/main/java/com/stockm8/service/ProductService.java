package com.stockm8.service;

import java.util.List;
import java.util.Map;

import com.stockm8.domain.vo.ProductVO;
/**
 * 상품 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 * - 상품 상세 정보 조회, 등록, 수정 등
 */
public interface ProductService {

	// 상품 등록 
	public void registerProduct(ProductVO product) throws Exception;
	
	// QR 등록 
    public void generateQRCode(int productId) throws Exception;

	// 상품 조회 
	public ProductVO getProductByID(int productId) throws Exception;
	
	// 상품 리스트
    public List<ProductVO> getProductsWithQRCode(int businessId) throws Exception;
    Map<Integer, String> getQRCodePathsByBusinessId(int businessId) throws Exception;
    
    // 회사 정보를 통해 상품 조회
	public List<ProductVO> getProductsByBusinessId(int businessId) throws Exception;

    ProductVO getProductByBarcode(String productBarcode) throws Exception;
	
}

