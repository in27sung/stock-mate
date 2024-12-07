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
    private CategoryDAO cdao;

    @Override
    public void addCategory(CategoryVO vo) throws Exception {
    	
    	System.out.println(" 카테고리 등록 실행 ");
    	
    	// 기본 businessId 설정
        vo.setBusinessId(1);  

        // 상위 카테고리가 없으면 대분류, 있으면 소분류로 설정
        if (vo.getParentId() == null) {
            vo.setLevel(1);  // 대분류
        } else {
            vo.setLevel(2);  // 소분류
        }

        // 현재 시간을 생성 시간으로 설정
        vo.setCreatedAt(new Timestamp(System.currentTimeMillis()));

    	System.out.println(" DAO의 카테고리 등록 메서드 호출");
        cdao.insertCategory(vo);
    }

    @Override
    public List<CategoryVO> getAllCategories() throws Exception {
    	System.out.println(" getAllCategories() 호출 ");
    	
        return cdao.selectAllCategories(); 
    }
    
    // 카테고리 수정
    @Override
    public void updateCategory(CategoryVO vo) throws Exception {
        // 카테고리 수정
        cdao.updateCategory(vo);
    }


    // 카테고리와 상위 카테고리 정보 조회
    @Override
    public CategoryVO getCategoryWithParents(int cId) throws Exception {
        // 카테고리 정보 조회
        CategoryVO vo = cdao.selectCategoryById(cId);

        // 상위 카테고리 조회
        CategoryVO parentCategory = null;
        if (vo.getParentId() != null) {
            parentCategory = cdao.selectCategoryById(vo.getParentId());  // vo.getParentId() 사용
        }

        // 상위 카테고리 정보를 별도로 처리 (VO에 저장하지 않음)
        return vo;  // 단순히 카테고리 정보만 반환
    }
    
    // 카테고리 삭제
    @Override
    public void deleteCategory(int cId) throws Exception {
    	// 카테고리 삭제
    	cdao.deleteCategory(cId);
    }
}
