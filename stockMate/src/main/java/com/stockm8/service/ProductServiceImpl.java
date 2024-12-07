package com.stockm8.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockm8.domain.vo.ProductVO;
import com.stockm8.persistence.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	@Override
	public void registerProduct(ProductVO productVO) throws Exception {
	    logger.info("registerProduct(ProductVO productVO) 호출");
	    if (productVO == null || productVO.getName() == null) {
	        throw new IllegalArgumentException("상품 정보가 유효하지 않습니다.");
	    }

	    // DAO를 통해 상품 등록
	    productDAO.insertProduct(productVO);
	    logger.info("상품 등록 완료: {}", productVO);
	}
}
