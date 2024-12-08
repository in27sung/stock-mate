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
    List<CategoryVO> selectCategoriesByBusinessId(@Param("businessId") int businessId) throws Exception;
    
    // 카테고리 목록 유효성 조회
    boolean existsById(int categoryId, int businessId);
    
    // 카테고리 수정
    public void updateCategory(CategoryVO vo) throws Exception;
    
    // ID로 특정 카테고리 조회
    public CategoryVO selectCategoryById(int catergoryId) throws Exception;
    
    // 카테고리 삭제
    public void deleteCategory(int cId) throws Exception;
}
