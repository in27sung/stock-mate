package com.stockm8.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.ReceivingShipmentVO;
import com.stockm8.domain.vo.StockVO;

@Repository
public class ShipmentDAOImpl implements ShipmentDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentDAOImpl.class);
	
	private static final String NAMESPACE = "com.stockm8.mappers.shipmentMapper.";
	
	// 디비 연결 객체 주입
	@Inject
	private SqlSession sqlSession;
	
	// 입고 메인 오늘 들어올 리스트
	@Override
	public List<ReceivingShipmentVO> selectShipmentList(int businessId) throws Exception {
		logger.info("selectShipmentList() 호출");
		
		return sqlSession.selectList(NAMESPACE + "getShipmentList", businessId); 
	}
	
	// 입고 메인 오늘 들어올 리스트
	@Override
	public List<ReceivingShipmentVO> selectYesterdayShipmentList(int businessId) throws Exception {
		logger.info("selectYesterdayShipmentList() 호출");
		
		return sqlSession.selectList(NAMESPACE + "getYesterdayShipmentList", businessId); 
	}
		
	// 입고 메인 오늘 들어올 리스트
	@Override
	public List<ReceivingShipmentVO> selectTDBYShipmentList(int businessId) throws Exception {
		logger.info("selectTDBYShipmentList() 호출");
		
		return sqlSession.selectList(NAMESPACE + "getTDBYShipmentList", businessId); 
	}
	
	// 입고 내역 히스토리 리스트
	@Override
	public List<ReceivingShipmentVO> selectShipmentHistoryList(Criteria cri, int businessId) throws Exception {
		logger.info("selectShipmentHistoryList() 호출");
		
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("cri", cri);
		paramMap.put("businessId", businessId);	   
		
		return sqlSession.selectList(NAMESPACE + "getShipmentHistoryList", paramMap);
	}

	// 입고 내역 검색
	@Override
	public List<ReceivingShipmentVO> selectHistoryByDateRange(String startDate, String endDate, String keyword, Criteria cri, int businessId) throws Exception {
		logger.info("selectHistoryByDateRange() 호출");
		
	    Map<String, Object> paramMap = new HashMap();
	    paramMap.put("startDate", startDate);
	    paramMap.put("endDate", endDate);
	    paramMap.put("keyword", keyword);
	    paramMap.put("cri", cri);
	    paramMap.put("businessId", businessId);	    
	    
		return sqlSession.selectList(NAMESPACE + "getHistoryByDateRange", paramMap);
	}

	@Override
	public int selectTotalCountBySearch(String startDate, String endDate, String keyword, int businessId) throws Exception {
	    
		Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("startDate", startDate);
	    paramMap.put("endDate", endDate);
	    paramMap.put("keyword", keyword);
	    paramMap.put("businessId", businessId);	
	    return sqlSession.selectOne(NAMESPACE + "getTotalCountBySearch", paramMap);
	}
	
	@Override
	public int selectTotalCount(int businessId) throws Exception {
		logger.info("selectTotalCount() 호출");
		
		return sqlSession.selectOne(NAMESPACE + "getTotalCount", businessId);
	}

	@Override
	public void insertShipment(int businessId, Long userId) throws Exception {
		logger.info("insertShipment() 호출");
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("businessId", businessId);
		paramMap.put("userId", userId);
		sqlSession.insert(NAMESPACE + "insertShipment", paramMap);
	}
	
  @Override
	public List<StockVO> selectQuantityCheckByBarcode(int businessId, String barcode, Integer receivingShipmentNo) throws Exception {
		logger.info("selectShipment() 호출");
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("businessId", businessId);	   
		paramMap.put("barcode", barcode);
		paramMap.put("receivingShipmentNo", receivingShipmentNo);
		
		return sqlSession.selectList(NAMESPACE + "selectQuantityCheckByBarcode", paramMap);
	}

	@Override
	public int updateIncreseStock(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemId) throws Exception {
		logger.info("updateDecreaseStock() 호출");
	    // 매개변수 묶기
	    Map<String, Object> params = new HashMap<>();
	    params.put("businessId", businessId);
	    params.put("barcode", barcode);
	    params.put("receivingShipmentNo", receivingShipmentNo);
	    params.put("orderItemId", orderItemId);
	    

	    // SQL 실행
	    return sqlSession.update(NAMESPACE + "updateIncreseStock", params);
	}

	@Override
	public int selectStockByBarcode(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemId) throws Exception {
		logger.info("selectStockByBarcode() 호출");
		Map<String, Object> params = new HashMap<>();
	    params.put("businessId", businessId);
	    params.put("barcode", barcode);
	    params.put("receivingShipmentNo", receivingShipmentNo);
	    params.put("orderItemId", orderItemId);
		return sqlSession.selectOne(NAMESPACE + "selectStockByBarcode", params);
	}

	@Override
	public int selectReservedQuantity(int businessId, String barcode, Integer receivingShipmentNo, Integer orderItemId) throws Exception {
		logger.info("selectReservedQuantity() 호출");
		Map<String, Object> params = new HashMap<>();
	    params.put("businessId", businessId);
	    params.put("barcode", barcode);
	    params.put("receivingShipmentNo", receivingShipmentNo);
	    params.put("orderItemId", orderItemId);	    
		return sqlSession.selectOne(NAMESPACE + "selectReservedQuantity", params);
	}
	
	@Override
	public ProductVO selectProductNameBarcode(int businessId, String barcode, Integer receivingShipmentNo) throws Exception {
		logger.info("selectProductNameBarcode() 호출");
		Map<String, Object> params = new HashMap<>();
		params.put("businessId", businessId);
		params.put("barcode", barcode);
		params.put("receivingShipmentNo", receivingShipmentNo);
		return sqlSession.selectOne(NAMESPACE + "selectProductNameBarcode", params);
	}

	@Override
	public void updateShipmentStatusToComplete(int businessId, String barcode, Integer receivingShipmentNo, int orderItemId, Long userId) throws Exception {
		logger.info("updateShipmentStatusToComplete() 호출");
		Map<String, Object> params = new HashMap<>();
		params.put("businessId", businessId);
		params.put("barcode", barcode);
		params.put("receivingShipmentNo", receivingShipmentNo);
		params.put("orderItemId", orderItemId);
		params.put("userId", userId);
		sqlSession.update(NAMESPACE + "updateShipmentStatusToComplete", params);
		
	}

	@Override
	public List<ReceivingShipmentVO> selectShipmentShipmentNo(int businessId, Integer receivingShipmentNo, int orderItemId) throws Exception {
		logger.info("selecttReceivingShipmentNo() 호출");
		Map<String, Object> params = new HashMap<>();
		params.put("businessId", businessId);
		params.put("receivingShipmentNo", receivingShipmentNo);
		params.put("orderItemId", orderItemId);
		
		return sqlSession.selectList(NAMESPACE + "getReceivingShipmentNo", params);
	}
	
	
	
	
	
	
	
	
} // ShipmentDAOImpl end
