package com.stockm8.service;

import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.vo.WarehouseVO;  // 추가된 VO
import com.stockm8.persistence.FilterCriteria;
import com.stockm8.domain.vo.CategoryVO;
import java.util.List;
import java.util.Map;

public interface StockService {
	
	// 재고 등록
	public void registerStock(StockVO stock) throws Exception;

    // 사업자 ID에 해당하는 창고 목록 조회
    public List<WarehouseVO> getWarehousesByBusinessId(int businessId) throws Exception;

    // 사업자 ID에 해당하는 재고 목록 조회
    public List<StockVO> getStockListByBusinessId(int businessId) throws Exception;

    // 필터링된 재고 목록 조회 (정렬 기준 추가)
    public List<StockVO> getStockList(FilterCriteria criteria, String sortOrder) throws Exception;

    // 카테고리 목록 조회
    public List<CategoryVO> getCategoryList() throws Exception;

    // 사업자 ID로 재고정보와 카테고리 정보 가져오기 (황인성)
    public Map<String, Object> getStockAndCategories(int businessId) throws Exception;
    
    // 사용 가능한 재고 자동 계산
    // public int calculateAvailableStock(StockVO stock) throws Exception;
}
