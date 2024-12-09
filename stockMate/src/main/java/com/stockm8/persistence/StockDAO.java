package com.stockm8.persistence;

import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.WarehouseVO;
import com.stockm8.domain.vo.CategoryVO;

import java.util.List;

public interface StockDAO {

    // 사업자 ID에 해당하는 재고 목록 조회
    public List<StockVO> selectAllStockByBusinessId(int businessId) throws Exception;

    // 상품 ID로 상품 상세 조회
    public ProductVO selectProductById(int productId) throws Exception;

    // 필터링된 재고 목록 조회
    public List<StockVO> selectFilteredStocks(String warehouseName, String categoryName, String sortOrder) throws Exception;

    // 창고 목록 조회
    public List<WarehouseVO> selectAllWarehouses() throws Exception;

    // 카테고리 목록 조회
    public List<CategoryVO> selectAllCategories() throws Exception;
}
