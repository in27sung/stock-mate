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

    private static final String NAMESPACE = "com.stockm8.mapper.CategoryMapper.";
    
    // 카테고리 등록
    @Override
    public void insertCategory(CategoryVO category) throws Exception {
        sqlSession.insert(NAMESPACE + "insertCategory", category);
    }
    
    // 카테고리 목록 조회
    @Override
    public List<CategoryVO> selectAllCategories() throws Exception {
        return sqlSession.selectList(NAMESPACE + "selectAllCategories");
    }
    
    // 특정 사업자(businessId) 소속의 카테고리 목록 조회
    @Override

    public List<CategoryVO> selectCategoriesByBusinessId(int businessId) throws Exception {
        return sqlSession.selectList(NAMESPACE + "selectCategoriesById", businessId);
    }
    
    // 카테고리ID로 카테고리명 조회
	@Override
	public String selectCategoryNameById(int categoryId) throws Exception {
		
        return sqlSession.selectOne(NAMESPACE + "selectCategoryNameById", categoryId);
	}
	// 카테고리 존재 여부 확인
	@Override
    public boolean existsById(int categoryId, int businessId) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("businessId", businessId);
        
        Integer count = sqlSession.selectOne(NAMESPACE + "existsById", params);
        return count != null && count > 0;
    }

    
    // 카테고리 수정
    @Override
    public void updateCategory(CategoryVO category) throws Exception {
        sqlSession.update(NAMESPACE + "updateCategory", category);
    }
    
    // 카테고리 ID로 조회
    @Override
    public CategoryVO selectCategoryById(int categoryId) throws Exception {
        return sqlSession.selectOne(NAMESPACE + "selectCategoryById", categoryId);
    }
    
    // 카테고리 삭제
    @Override
    public void deleteCategory(int categoryId) throws Exception {
        sqlSession.delete(NAMESPACE + "deleteCategory", categoryId);
    }
}
