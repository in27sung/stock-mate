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
public class ReceivingDAOImpl implements ReceivingDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ReceivingDAOImpl.class);
	
	private static final String NAMESPACE = "com.stockm8.mappers.receivingMapper.";
	
	// 디비 연결 객체 주입
	@Inject
	private SqlSession sqlSession;
	
	// 입고 메인 오늘 들어올 리스트
	@Override
	public List<ReceivingShipmentVO> selectReceivingList(int businessId) throws Exception {
		logger.info("selectReceivingList() 호출");
		
		return sqlSession.selectList(NAMESPACE + "getReceivingList", businessId); 
	}
	
	// 입고 메인 오늘 들어올 리스트
	@Override
	public List<ReceivingShipmentVO> selectYesterdayReceivingList(int businessId) throws Exception {
		logger.info("selectYesterdayReceivingList() 호출");
		
		return sqlSession.selectList(NAMESPACE + "getYesterdayReceivingList", businessId); 
	}
		
	// 입고 메인 오늘 들어올 리스트
	@Override
	public List<ReceivingShipmentVO> selectTDBYReceivingList(int businessId) throws Exception {
		logger.info("selectTDBYReceivingList() 호출");
		
		return sqlSession.selectList(NAMESPACE + "getTDBYReceivingList", businessId); 
	}
	
	// 입고 내역 히스토리 리스트
	@Override
	public List<ReceivingShipmentVO> selectReceivingHistoryList(Criteria cri, int businessId) throws Exception {
		logger.info("selectReceivingHistoryList() 호출");
		
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("cri", cri);
		paramMap.put("businessId", businessId);	   
		
		return sqlSession.selectList(NAMESPACE + "getReceivingHistoryList", paramMap);
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
	public void insertReceiving(int businessId, Long userId) throws Exception {
		logger.info("insertReceiving() 호출");
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("businessId", businessId);
		paramMap.put("userId", userId);
		sqlSession.insert(NAMESPACE + "insertReceiving", paramMap);
	}
	
  @Override
	public List<StockVO> selectQuantityCheckByBarcode(int businessId, String barcode, Integer receivingShipmentNo) throws Exception {
		logger.info("selectReceiving() 호출");
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
	public void updateReceivingStatusToComplete(int businessId, String barcode, Integer receivingShipmentNo, int orderItemId, Long userId) throws Exception {
		logger.info("updateReceivingStatusToComplete() 호출");
		Map<String, Object> params = new HashMap<>();
		params.put("businessId", businessId);
		params.put("barcode", barcode);
		params.put("receivingShipmentNo", receivingShipmentNo);
		params.put("orderItemId", orderItemId);
		params.put("userId", userId);		
		sqlSession.update(NAMESPACE + "updateReceivingStatusToComplete", params);
		
	}

	@Override
	public List<ReceivingShipmentVO> selectReceivingShipmentNo(int businessId, Integer receivingShipmentNo, int orderItemId) throws Exception {
		logger.info("selecttReceivingShipmentNo() 호출");
		Map<String, Object> params = new HashMap<>();
		params.put("businessId", businessId);
		params.put("receivingShipmentNo", receivingShipmentNo);
		params.put("orderItemId", orderItemId);
		
		return sqlSession.selectList(NAMESPACE + "getReceivingShipmentNo", params);
	}
	
	
	
	
	
	
	
	
} // ReceivingDAOImpl end
