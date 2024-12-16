package com.stockm8.service;

import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.vo.WarehouseVO;
import com.stockm8.persistence.FilterCriteria;
import com.stockm8.domain.vo.CategoryVO;
import com.stockm8.persistence.StockDAO;
import com.stockm8.persistence.WarehouseDAO;
import com.stockm8.persistence.CategoryDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Inject
    private StockDAO stockDAO;

    @Inject
    private WarehouseDAO warehouseDAO;

    @Inject
    private CategoryDAO categoryDAO;

    // 재고 등록
    @Override
    public void registerStock(StockVO stock) throws Exception {
        stockDAO.insertStock(stock);
    }

    // 사업자 ID에 해당하는 창고 목록 조회
    @Override
    public List<WarehouseVO> getWarehousesByBusinessId(int businessId) throws Exception {
        return warehouseDAO.selectWarehousesByBusinessId(businessId);  // WarehouseDAO에서 창고 목록 조회
    }

    // 사업자 ID에 해당하는 재고 목록 조회
    @Override
    public List<StockVO> getStockListByBusinessId(int businessId) throws Exception {
        return stockDAO.selectOnlyStockByBusinessId(businessId);
    }

    // 필터링된 재고 목록 조회 (정렬 기준 추가)
    @Override
    public List<StockVO> getStockList(FilterCriteria criteria, String sortOrder) throws Exception {
        return stockDAO.selectFilteredStocks(criteria, sortOrder);  // StockDAO에서 필터링된 재고 목록 조회
    }

    // 카테고리 목록 조회
    @Override
    public List<CategoryVO> getCategoryList() throws Exception {
        return categoryDAO.selectAllCategories();  // CategoryDAO에서 카테고리 목록 조회
    }

    // 사업자 ID로 재고정보와 카테고리 정보 가져오기
    @Override
    public Map<String, Object> getStockAndCategories(int businessId) throws Exception {
        // 재고 목록 조회
        List<StockVO> stockList = stockDAO.selectStockListByBusinessId(businessId);
        // 카테고리 목록 조회
        List<CategoryVO> categoryList = categoryDAO.selectCategoriesByBusinessId(businessId);

        // 결과를 Map에 담아서 반환
        Map<String, Object> result = new HashMap<>();
        result.put("stockList", stockList);
        result.put("categoryList", categoryList);

        return result;
    }

    // 사용 가능한 재고 자동 계산 (주석 처리)
    // @Override
    // public int calculateAvailableStock(StockVO stock) throws Exception {
    //     // DB에서 available stock을 자동으로 계산하는 방식이므로 이 메서드는 필요하지 않음.
    //     return 0;  // 구현을 필요로 할 경우 로직 추가
    // }
}