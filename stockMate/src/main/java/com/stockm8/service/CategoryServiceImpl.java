package com.stockm8.service;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.stockm8.domain.vo.CategoryVO;
import com.stockm8.persistence.CategoryDAO;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Inject
    private CategoryDAO categoryDAO;

    @Override
    public void addCategory(CategoryVO category) throws Exception {
    	
    	System.out.println(" 카테고리 등록 실행 ");
    	
    	// 기본 businessId 설정
        category.setBusinessId(1);  

        // 상위 카테고리가 없으면 대분류, 있으면 소분류로 설정
        if (category.getParentId() == null) {
            category.setLevel(1);  // 대분류
        } else {
            category.setLevel(2);  // 소분류
        }

        // 현재 시간을 생성 시간으로 설정
        category.setCreatedAt(new Timestamp(System.currentTimeMillis()));

    	System.out.println(" DAO의 카테고리 등록 메서드 호출");
    	categoryDAO.insertCategory(category);
    }

    @Override
    public List<CategoryVO> getAllCategories() throws Exception {
    	System.out.println(" getAllCategories() 호출 ");
    	
        return categoryDAO.selectAllCategories(); 
    }
    
    // 특정 사업자(businessId) 소속의 카테고리 목록을 조회
    @Override
	public List<CategoryVO> getCategoriesByBusinessId(int businessId) throws Exception {
    	System.out.println(" getCategoriesByBusinessId() 호출 ");

    	return categoryDAO.selectCategoriesByBusinessId(businessId);
	}
    
    // 카테고리 수정
    @Override
    public void updateCategory(CategoryVO category) throws Exception {
        // 카테고리 수정
    	categoryDAO.updateCategory(category);
    }

	// 카테고리와 상위 카테고리 정보 조회
    @Override
    public CategoryVO getCategoryWithParents(int cId) throws Exception {
        // 카테고리 정보 조회
        CategoryVO category = categoryDAO.selectCategoryById(cId);

        // 상위 카테고리 조회
        CategoryVO parentCategory = null;
        if (category.getParentId() != null) {
            parentCategory = categoryDAO.selectCategoryById(category.getParentId());  // vo.getParentId() 사용
        }

        // 상위 카테고리 정보를 별도로 처리 (VO에 저장하지 않음)
        return category;  // 단순히 카테고리 정보만 반환
    }
    
    // 카테고리 삭제
    @Override
    public void deleteCategory(int cId) throws Exception {
    	// 카테고리 삭제
    	categoryDAO.deleteCategory(cId);
    }


    
    
}
