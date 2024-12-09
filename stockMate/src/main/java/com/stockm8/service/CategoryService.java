package com.stockm8.service;

import java.util.List;

import com.stockm8.domain.vo.CategoryVO;

public interface CategoryService {
	
	// 카테고리 등록
    public void addCategory(CategoryVO vo) throws Exception;
    
    // 카테고리 목록 조회
    public List<CategoryVO> getAllCategories() throws Exception;
    
    // 특정 사업자(businessId) 소속의 카테고리 목록을 조회
    public List<CategoryVO> getCategoriesByBusinessId(int businessId) throws Exception;
    
    // 카테고리ID로 카테고리명 조회
    public void getCategoryNameById(int categoryId) throws Exception;
    
    // 카테고리 수정
    public void updateCategory(CategoryVO vo) throws Exception;
    
    // 카테고리와 상위 카테고리 목록 조회
    public CategoryVO getCategoryWithParents(int cId) throws Exception;
    
    // 카테고리 삭제
    public void deleteCategory(int cId) throws Exception;
    
}
