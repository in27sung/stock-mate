package com.stockm8.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.stockm8.domain.vo.ProductVO;

public interface ProductDAO {
	
	// 상품 등록 
	public void insertProduct(ProductVO product) throws Exception;
	
	// QR코드 등록 
    public void updateQRCodePath(ProductVO product) throws Exception;

    // 상품 ID로 상품 조회
    ProductVO getProductById(int productId) throws Exception;
  
    // 상품 리스트
    public List<ProductVO> selectProductsWithQRCode(@Param("businessId") int businessId) throws Exception;

    // 회사 정보를 통해 상품 조회
    public List<ProductVO> selectProductsByBusinessId(@Param("businessId") int businessId) throws Exception;
}
