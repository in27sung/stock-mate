package com.stockm8.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.dto.QRCodeDTO;
import com.stockm8.domain.dto.StockQRCodeDTO;

@Repository
public class QRCodeDAOImpl implements QRCodeDAO {

    // 디비 연결 객체 주입
    @Autowired
    private SqlSession sqlSession;

    private static final String NAMESPACE = "com.stockm8.mapper.QRCodeMapper.";

    @Override
    public void updateQRCodePathByProductId(StockQRCodeDTO stockBarcode) {
        sqlSession.insert(NAMESPACE + "updateQRCodePathByProductId", stockBarcode);
    }

	@Override
	public StockQRCodeDTO selectQRCodeByBarcode(String productBarcode) {
		return sqlSession.selectOne(NAMESPACE + "selectQRCodeByBarcode", productBarcode);
	}    
	
    @Override
	public StockQRCodeDTO selectQRCodeByProductId(Integer productId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "selectQRCodeByProductId", productId);
	}

	@Override
    public List<QRCodeDTO> selectQRCodePathsByBusinessId(int businessId) throws Exception {
        return sqlSession.selectList(NAMESPACE + "selectQRCodePathsByBusinessId", businessId);
    }
}