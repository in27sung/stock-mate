package com.stockm8.persistence;

import java.util.List;

import com.stockm8.domain.vo.CategoryVO;

public interface CategoryDAO {
	
	// 카테고리 등록
	public void insertCategory(CategoryVO vo) throws Exception;
    
	// 카테고리 목록 조회
    public List<CategoryVO> selectAllCategories() throws Exception;
    
    // 카테고리 목록 유효성 조회
    boolean existsById(int categoryId, int businessId);
    
    // 카테고리 수정
    public void updateCategory(CategoryVO vo) throws Exception;
    
    // ID로 특정 카테고리 조회
    public CategoryVO selectCategoryById(int catergoryId) throws Exception;
    
    // 카테고리 삭제
    public void deleteCategory(int cId) throws Exception;
}
