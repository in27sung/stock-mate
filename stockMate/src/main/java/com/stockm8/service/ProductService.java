package com.stockm8.service;

import com.stockm8.domain.vo.ProductVO;
/**
 * 상품 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 * - 상품 상세 정보 조회, 등록, 수정 등
 */
public interface ProductService {

	// 상품 등록 
	public void registerProduct(ProductVO product) throws Exception;
	
	// 상품 조회 
	public ProductVO getProductByID(int productId) throws Exception;
}

