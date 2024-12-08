package com.stockm8.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.vo.WarehouseVO;

@Repository
public class WarehouseDAOImpl implements WarehouseDAO {

	private static final Logger logger = LoggerFactory.getLogger(WarehouseDAOImpl.class);

	// 디비연결 객체 주입
	@Autowired
	private SqlSession sqlSession;

	private static final String NAMESPACE = "com.stockm8.mapper.WarehouseMapper.";

	@Override
	public void insertWarehouse(WarehouseVO warehouse) throws Exception {
		logger.info("insertWarehouse(WarehouseVO wVO) 호출");
		sqlSession.insert(NAMESPACE + "insertWarehouse", warehouse);
	}

	@Override
	public List<WarehouseVO> selectWarehousesByBusinessId(Integer businessId) throws Exception {
		logger.info("getWarehousesByBusinessId(Long businessId) 호출");

		return sqlSession.selectList(NAMESPACE + "selectWarehousesByBusinessId", businessId);
	}

	@Override
	public boolean existsById(int warehouseId, int businessId) {
		Map<String, Object> params = new HashMap<>();
		params.put("warehouseId", warehouseId);
		params.put("businessId", businessId);
		Integer count = sqlSession.selectOne(NAMESPACE + "existsById", params);
		return count != null && count > 0;
	}

}
