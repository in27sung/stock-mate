package com.stockm8.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.stockm8.domain.vo.CategoryVO;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Inject
    private SqlSession sqlSession;

    private static final String NAMESPACE
    				  = "com.stockm8.mapper.CategoryMapper";
    
    // 카테고리 등록
    @Override
    public void insertCategory(CategoryVO vo) throws Exception {
    	
    	// mapper SQL 구문 호출
        sqlSession.insert(NAMESPACE + ".insertCategory", vo);
    }
    
    // 카테고리 목록 조회
    @Override
    public List<CategoryVO> selectAllCategories() throws Exception {
    	
    	// mapper SQL 구문 호출
        return sqlSession.selectList(NAMESPACE + ".selectAllCategories"); 
    }
    
    @Override
    public boolean existsById(int categoryId, int businessId) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("businessId", businessId);
        return sqlSession.selectOne(NAMESPACE + "existsById", params);
    }
    
    // 카테고리 수정
    @Override
    public void updateCategory(CategoryVO vo) throws Exception {
        sqlSession.update(NAMESPACE + ".updateCategory", vo);
    }

    // 카테고리 ID로 조회
    @Override
    public CategoryVO selectCategoryById(int catergoryId) throws Exception {
        return sqlSession.selectOne(NAMESPACE + ".selectCategoryById", catergoryId);
    }
    
    // 카테고리 삭제
    @Override
    public void deleteCategory(int cId) throws Exception {
    	sqlSession.delete(NAMESPACE + ".deleteCategory", cId);
    }
}
