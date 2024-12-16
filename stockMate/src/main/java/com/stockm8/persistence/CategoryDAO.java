package com.stockm8.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.stockm8.domain.vo.CategoryVO;

public interface CategoryDAO {

    // 카테고리 등록
    public void insertCategory(CategoryVO vo) throws Exception;

    // 카테고리 목록 조회
    public List<CategoryVO> selectAllCategories() throws Exception;
    
    // 특정 사업자(businessId) 소속의 카테고리 목록 조회
    public List<CategoryVO> selectCategoriesByBusinessId(@Param("businessId") int businessId) throws Exception;
    
    // 카테고리명 조회
    public String selectCategoryNameById(int categoryId) throws Exception;
    
    // 카테고리 이름 중복 체크
    public int selectCategoryCountByName(String categoryName) throws Exception;

    // 하위 카테고리들의 부모 ID를 갱신하는 메서드
    public void updateSubCategoryParentId(int parentId, int categoryId) throws Exception;
    
    // 카테고리 존재 여부 확인
    public boolean existsById(@Param("categoryId") int categoryId, @Param("businessId") int businessId);
    
    // 카테고리 수정
    public void updateCategory(CategoryVO vo) throws Exception;

    // ID로 특정 카테고리 조회
    public CategoryVO selectCategoryById(@Param("categoryId") int categoryId) throws Exception;

    // 카테고리 논리 삭제 처리
    public void deleteCategory(@Param("categoryId") int categoryId) throws Exception;
    
    // 부모 카테고리만 조회
    public List<CategoryVO> selectParentCategories() throws Exception;
}
