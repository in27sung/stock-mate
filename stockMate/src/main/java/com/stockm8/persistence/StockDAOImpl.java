package com.stockm8.persistence;

import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.vo.CategoryVO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StockDAOImpl implements StockDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(StockDAOImpl.class);

    @Inject
    private SqlSession sqlSession;

    private static final String NAMESPACE = "com.stockm8.mapper.StockMapper.";
    
    // 재고 등록
    @Override
	public void insertStock(StockVO stock) throws Exception {
        sqlSession.insert(NAMESPACE + "insertStock", stock);
	}
    
    // 사업자 ID에 해당하는 재고 목록 조회
    @Override
    public List<StockVO> selectStockListByBusinessId(int businessId) throws Exception {
        return sqlSession.selectList(NAMESPACE + "selectStockListByBusinessId", businessId);
    }

    // 필터링된 재고 목록 조회 (정렬 기준 포함)
    @Override
    public List<StockVO> selectFilteredStocks(FilterCriteria criteria, String sortOrder) throws Exception {
        // 파라미터를 Map으로 묶어서 MyBatis 쿼리로 전달
        Map<String, Object> params = new HashMap<>();
        params.put("criteria", criteria);
        params.put("sortOrder", sortOrder);  // 정렬 기준 추가

        // MyBatis 매퍼에 정의된 SQL 쿼리 호출
        return sqlSession.selectList(NAMESPACE + "selectFilteredStocks", params);
    }
    
    // 사업자 ID에 해당하는 재고 목록 조회 (단순 조회)
    @Override
    public List<StockVO> selectOnlyStockByBusinessId(int businessId) throws Exception {
        return sqlSession.selectList(NAMESPACE + "selectOnlyStockByBusinessId", businessId);
	}
    // 카테고리 목록 조회
    @Override
    public List<CategoryVO> selectAllCategories() throws Exception {
        return sqlSession.selectList(NAMESPACE + "selectAllCategories");
    }
    
   @Override
   public List<CategoryVO> selectAllCategories() throws Exception {
         return sqlSession.selectList(NAMESPACE + "selectAllCategories");
   }
}

