package com.stockm8.service;

import java.util.List;

import com.stockm8.domain.vo.CategoryVO;

public interface CategoryService {
    
    // 카테고리 등록
    public void addCategory(CategoryVO category) throws Exception;
    
    // 부모 카테고리 체크 후 카테고리 등록 (새 부모 카테고리 추가 여부 확인)
    public void registerCategoryWithParentCheck(CategoryVO category) throws Exception;
    
    // 카테고리와 상위 카테고리 목록 조회
    public CategoryVO getCategoryWithParents(int categoryId) throws Exception;
    
    // 카테고리 목록 조회
    public List<CategoryVO> getAllCategories() throws Exception;
    
    // 부모 카테고리 조회
    public List<CategoryVO> getParentCategories() throws Exception;
    
    // 카테고리 이름 중복 체크
    public boolean checkCategoryNameExists(String categoryName) throws Exception;
    
    // 특정 사업자(businessId) 소속의 카테고리 목록을 조회
    public List<CategoryVO> getCategoriesByBusinessId(int businessId) throws Exception;

    // 카테고리ID로 카테고리명 조회
    public String getCategoryNameById(int categoryId) throws Exception;

    // 카테고리 수정	
    public void updateCategory(CategoryVO category) throws Exception;
    
    // 카테고리 삭제
    public void deleteCategory(int categoryId) throws Exception;
    
    
}
