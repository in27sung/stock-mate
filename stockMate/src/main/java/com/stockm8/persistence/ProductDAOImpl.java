package com.stockm8.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.dto.BarcodeDTO;
import com.stockm8.domain.vo.ProductVO;

@Repository
public class ProductDAOImpl implements ProductDAO {
	
	// 디비연결 객체 주입 
	@Autowired
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "com.stockm8.mapper.ProductMapper.";
	
	@Override
	public void insertProduct(ProductVO product) throws Exception {
		
		// mapper 호출 및 실행 
		sqlSession.insert(NAMESPACE + "insertProduct", product);
		
	}
	
	
	@Override
	public void updateQRCodePath(ProductVO product) throws Exception {
		
		sqlSession.update(NAMESPACE + "updateQRCodePath", product);		
	}


	@Override
	public ProductVO getProductById(int productId) throws Exception{

	    return sqlSession.selectOne(NAMESPACE + "getProductById", productId);
	}
	

	@Override
	public List<ProductVO> selectProductsWithQRCode(int businessId) throws Exception{
	    return sqlSession.selectList(NAMESPACE + "selectProductsWithQRCode", businessId);
	}

    // 회사 정보를 통해 상품 조회
	@Override
	public List<ProductVO> selectProductsByBusinessId(int businessId) throws Exception {
	    return sqlSession.selectList(NAMESPACE + "selectProductsByBusinessId", businessId);
	}

	@Override
	public ProductVO getProductByBarcode(String productBarcode) throws Exception {
	    return sqlSession.selectOne(NAMESPACE + "selectProductByBarcode", productBarcode);
	}

	@Override
	public void updateBarcodePathByProductId(BarcodeDTO barcode) {
		sqlSession.update(NAMESPACE + "updateBarcodePathByProductId", barcode);		

	}
	
}
