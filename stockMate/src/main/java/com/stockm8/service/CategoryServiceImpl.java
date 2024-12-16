package com.stockm8.service;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stockm8.domain.vo.CategoryVO;
import com.stockm8.persistence.CategoryDAO;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
    @Inject
    private CategoryDAO categoryDAO;
    
    // 카테고리 등록
    @Override
    public void addCategory(CategoryVO category) throws Exception {
        logger.info("CategoryVO 내용: {}", category);

        // category가 null일 경우 예외 처리
        if (category == null) {
            throw new IllegalArgumentException("CategoryVO 객체는 null일 수 없습니다.");
        }

        logger.info("카테고리 등록 실행");

        // 카테고리 이름 중복 체크
        if (checkCategoryNameExists(category.getCategoryName())) {
            throw new IllegalArgumentException("이미 존재하는 카테고리 이름입니다.");
        }

        // businessId가 null이라면 예외 처리 (필수 값)
        if (category.getBusinessId() == null) {
            throw new IllegalArgumentException("businessId는 null일 수 없습니다.");
        }

        // parentId에 따라 레벨 지정
        if (category.getParentId() == null) {
            // 상위 카테고리가 없으므로 대분류로 설정
            category.setLevel(1);
        } else {
            // 상위 카테고리가 존재하면 해당 상위 카테고리의 레벨을 기반으로 설정
            CategoryVO parentCategory = categoryDAO.selectCategoryById(category.getParentId());
            if (parentCategory == null) {
                throw new IllegalArgumentException("존재하지 않는 상위 카테고리 ID입니다.");
            }
            category.setLevel(parentCategory.getLevel() + 1); // 상위 레벨 + 1
        }

        // createdAt 설정
        if (category.getCreatedAt() == null) {
            category.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }

        // DAO의 insertCategory 호출
        categoryDAO.insertCategory(category);  // 카테고리 등록 후 categoryId가 설정됨
        logger.info("카테고리 등록 후 Category ID: " + category.getCategoryId());  // 추가 로그로 categoryId 확인
    }
    
    // 카테고리 목록 조회
    @Override
    public List<CategoryVO> getAllCategories() throws Exception {
    	logger.info(" getAllCategories() 호출 ");
        return categoryDAO.selectAllCategories(); 
    }
    
    // 카테고리 이름 중복체크
    @Override
    public boolean checkCategoryNameExists(String categoryName) throws Exception {
        // 카테고리 이름 중복 체크	
        int count = categoryDAO.selectCategoryCountByName(categoryName);
        return count > 0;
    }
    
    // 카테고리 수정
    @Override
    public void updateCategory(CategoryVO category) throws Exception {
    	category.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        categoryDAO.updateCategory(category);
    }
    
    // 카테고리 및 상위 카테고리 정보 조회
    @Override
    public CategoryVO getCategoryWithParents(int categoryId) throws Exception {
        // 카테고리 정보 조회
        CategoryVO category = categoryDAO.selectCategoryById(categoryId);

        // parentId가 null이면 부모 카테고리로 처리
        if (category.getParentId() != null) {
        	CategoryVO parentCategory = categoryDAO.selectCategoryById(category.getParentId());
        	 if (parentCategory != null) {
        		 category.setLevel(parentCategory.getLevel() + 1); // 부모 레벨 + 1
        	 }
        } else {
        	category.setLevel(1); // 부모가 없으면 레벨 1
        }
        return category;  // 부모 카테고리와 상관없이 카테고리 정보만 반환
    }

    // 특정 사업자(businessId) 소속의 카테고리 목록을 조회(삭제하지 마세요)
    @Override
	public List<CategoryVO> getCategoriesByBusinessId(int businessId) throws Exception {
    	logger.info(" getCategoriesByBusinessId() 호출 ");
    	return categoryDAO.selectCategoriesByBusinessId(businessId);
	}

    // 카테고리ID로 카테고리명 조회(삭제하지 마세요)
    @Override
	public String getCategoryNameById(int categoryId) throws Exception {
    	logger.info(" getCategoryNameById() 호출 ");
    	return categoryDAO.selectCategoryNameById(categoryId);
	}
    
    // 카테고리 삭제
    @Override
    public void deleteCategory(int categoryId) throws Exception {
        // 카테고리 논리 삭제
        categoryDAO.deleteCategory(categoryId);
    }

    @Override
    public void registerCategoryWithParentCheck(CategoryVO category) throws Exception {
        // 부모 카테고리 없을 경우 대분류 처리
        if (category.getParentId() == null || category.getParentId() == 0) {
            category.setParentId(null);  // 부모 카테고리 설정되지 않음
            category.setLevel(1);  // 대분류
        } else {
            // 부모 카테고리 설정된 경우
            CategoryVO parentCategory = categoryDAO.selectCategoryById(category.getParentId());
            if (parentCategory == null) {
                throw new IllegalArgumentException("존재하지 않는 상위 카테고리 ID입니다.");
            }
            category.setLevel(parentCategory.getLevel() + 1);  // 부모 레벨 + 1
        }

        // 카테고리 등록 처리
        addCategory(category);
    }
    
    @Override
    public List<CategoryVO> getParentCategories() throws Exception {
        return categoryDAO.selectParentCategories();  // 부모 카테고리만 조회
    }
}