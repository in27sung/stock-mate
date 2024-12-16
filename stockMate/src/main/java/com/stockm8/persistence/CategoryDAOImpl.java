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
    public void insertCategory(CategoryVO vo) throws Exception {
        sqlSession.insert(NAMESPACE + "insertCategory", vo);
    }
    
    // 카테고리 목록 조회 (삭제되지 않은 카테고리만 조회)
    @Override
    public List<CategoryVO> selectAllCategories() throws Exception {
        try {
            return sqlSession.selectList(NAMESPACE + "selectAllCategories");
        } catch (Exception e) {
            throw new Exception("카테고리 목록 조회 실패", e);
        }
    }
    
    // 특정 사업자(businessId) 소속의 카테고리 목록 조회 (삭제되지 않은 카테고리만 조회)
    @Override
    public List<CategoryVO> selectCategoriesByBusinessId(int businessId) throws Exception {
        return sqlSession.selectList(NAMESPACE + "selectCategoriesByBusinessId", businessId);
    }

    // 카테고리ID로 카테고리명 조회
    @Override
    public String selectCategoryNameById(int categoryId) throws Exception {
        return sqlSession.selectOne(NAMESPACE + "selectCategoryNameById", categoryId);
    }
    
    // 카테고리 이름 중복 체크
    @Override
    public int selectCategoryCountByName(String categoryName) throws Exception {
        // SQL 쿼리 실행
        return sqlSession.selectOne(NAMESPACE + "selectCategoryCountByName", categoryName);
    }
    
    // 하위 카테고리들의 부모 ID를 갱신하는 메서드
    public void updateSubCategoryParentId(int parentId, int categoryId) throws Exception {
    	Map<String, Object> params = new HashMap<>();
    	params.put("parentId", parentId);
    	params.put("categoryId", categoryId);
    	
    	sqlSession.update(NAMESPACE + "updateSubCategoryParentId", params);
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
    
    // 카테고리 ID로 조회 (삭제되지 않은 카테고리만 조회)
    @Override
    public CategoryVO selectCategoryById(int categoryId) throws Exception {
        return sqlSession.selectOne(NAMESPACE + "selectCategoryById", categoryId);
    }
    
    // 카테고리 논리 삭제 처리 (is_deleted를 true로 설정)
    @Override
    public void deleteCategory(int categoryId) throws Exception {
        sqlSession.update(NAMESPACE + "deleteCategory", categoryId);
    }
    
    // 부모 카테고리만 조회
    @Override
    public List<CategoryVO> selectParentCategories() throws Exception {
        return sqlSession.selectList(NAMESPACE + "selectParentCategories");
    }
}
