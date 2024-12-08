package com.stockm8.persistence;

import com.stockm8.domain.vo.ProductVO;

public interface ProductDAO {
	
	// 상품 등록 
	public void insertProduct(ProductVO product) throws Exception;
	
    // 상품 ID로 상품 조회
    ProductVO getProductById(int productId) throws Exception;
  
}
