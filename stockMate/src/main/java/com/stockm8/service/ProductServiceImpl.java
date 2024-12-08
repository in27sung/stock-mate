package com.stockm8.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockm8.domain.vo.ProductVO;
import com.stockm8.persistence.ProductDAO;

/**
 * ProductService 인터페이스를 구현한 클래스.
 * DAO를 호출하여 실제 비즈니스 로직을 수행한다.
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	@Override
	public void registerProduct(ProductVO product) throws Exception {
	    logger.info("registerProduct(ProductVO productVO) 호출");
	    if (product == null || product.getName() == null) {
	        throw new IllegalArgumentException("상품 정보가 유효하지 않습니다.");
	    }

	    // DAO를 통해 상품 등록
	    productDAO.insertProduct(product);
	    logger.info("상품 등록 완료: {}", product);
	}

	@Override
	public ProductVO getProductByID(int productId) throws Exception {
	    logger.info("getProductByID(int productId) 호출");
		return productDAO.getProductById(productId);
	}
}
