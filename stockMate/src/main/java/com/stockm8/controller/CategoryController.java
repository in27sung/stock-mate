package com.stockm8.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.stockm8.domain.vo.CategoryVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.CategoryService;
import com.stockm8.service.UserService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Inject
    private CategoryService categoryService;
    
    @Inject
    private UserService userService;
    
    // http://localhost:8088/category/register
    // 카테고리 등록 페이지 호출 (GET)
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerCategoryGET(Model model, HttpServletRequest request) throws Exception {
    	logger.info("registerCategoryGET(Model model, HttpServletRequest request) 호출");
    	// 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
		logger.info("session: {}", userId);
	    
	    // userId로 사용자 정보 조회
	    UserVO user = userService.getUserById(userId);
	    int businessId = user.getBusinessId();
	    logger.info("businessId: {}", businessId);
	    
        // 필요한 데이터 (예: 상위 카테고리 목록 등) 전달
        List<CategoryVO> categoryList = categoryService.getCategoriesByBusinessId(businessId);
        model.addAttribute("categoryList", categoryList);
        logger.info("Category List: {}", categoryList);

        // 카테고리 등록 폼을 보여주는 페이지로 이동
        return "category/register"; 
    }

    // 카테고리 등록 처리 (POST)
    @PostMapping("/register")
    public String registerCategory(@ModelAttribute CategoryVO categoryVO, 
                                   @SessionAttribute("userId") Long userId, 
                                   Model model) {
        try {
            // 사용자 정보 가져오기
            UserVO user = userService.getUserById(userId);
            int businessId = user.getBusinessId();
            categoryVO.setBusinessId(businessId);

            // 상위 카테고리 체크 및 등록
            categoryService.registerCategoryWithParentCheck(categoryVO);

            // 성공 메시지 설정
            model.addAttribute("toastMessage", "카테고리가 성공적으로 등록되었습니다.");
            model.addAttribute("toastType", "success");
        } catch (IllegalArgumentException e) {
            // 입력 데이터 관련 예외 처리
            logger.error("카테고리 등록 실패 - 잘못된 입력: {}", e.getMessage());

            // 실패 메시지 설정
            model.addAttribute("toastMessage", e.getMessage());
            model.addAttribute("toastType", "error");
        } catch (Exception e) {
            // 일반적인 예외 처리
            logger.error("카테고리 등록 중 예외 발생: ", e);

            // 실패 메시지 설정
            model.addAttribute("toastMessage", "카테고리 등록 중 오류가 발생했습니다. 다시 시도해주세요.");
            model.addAttribute("toastType", "error");
        }

        // 등록 페이지로 이동 (현재 페이지 유지)
        try {
            // 상위 카테고리 리스트를 다시 불러옴
            List<CategoryVO> categoryList = categoryService.getParentCategories();
            model.addAttribute("categoryList", categoryList);
        } catch (Exception e) {
            logger.error("카테고리 목록 불러오기 실패: ", e);
            model.addAttribute("toastMessage", "카테고리 목록을 불러오는 중 오류가 발생했습니다.");
            model.addAttribute("toastType", "error");
        }

        return "category/register"; // 현재 페이지 유지
    }
    
    // http://localhost:8088/category/list
    // 카테고리 목록 페이지 호출 (GET)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listCategoryGET(@SessionAttribute("userId") Long userId, Model model) throws Exception {
    	logger.info("listCategoryGET(Model model) 호출");
    	
        // 사용자 정보 가져오기
        UserVO user = userService.getUserById(userId);
        int businessId = user.getBusinessId();
        logger.info("Business ID for userId {}: {}", userId, businessId);
        
        // 카테고리 목록 조회
        List<CategoryVO> categories = categoryService.getCategoriesByBusinessId(businessId); 
        
        // JSP로 데이터 전달
        model.addAttribute("categories", categories); 
        return "category/list"; 
    }

    // 카테고리 수정 페이지 (GET)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editCategoryGET(@RequestParam("categoryId") int categoryId, Model model) throws Exception {
    	logger.info("editCategoryGET(@RequestParam(\"categoryId\") int categoryId, Model model) 호출");
    	
    	// 카테고리와 상위 카테고리 정보 가져오기
        CategoryVO vo = categoryService.getCategoryWithParents(categoryId);
        model.addAttribute("category", vo);
        
        // 상위 카테고리 정보 (부모 카테고리 리스트 가져오기)
        List<CategoryVO> parentCategories = categoryService.getParentCategories();  // 부모 카테고리 리스트를 가져오기
        model.addAttribute("parentCategories", parentCategories);

        return "category/edit";
    }

    // 카테고리 수정 처리 (POST)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editCategoryPOST(@ModelAttribute CategoryVO category) throws Exception {
    	logger.info("editCategoryPOST(@ModelAttribute CategoryVO vo) 실행");
    	
    	// 부모 카테고리가 비어 있으면 null로 처리
        if (category.getParentId() != null && category.getParentId().equals("")) {
        	category.setParentId(null);
            logger.info("부모 카테고리가 비어있어 null로 설정됨, categoryId: {}", category.getCategoryId());
        }
        // 카테고리 수정
        categoryService.updateCategory(category);
        logger.info("카테고리 수정 완료, categoryId: {}", category.getCategoryId());

        return "redirect:/category/list";  // 수정 후 목록 페이지로 리다이렉트
    }

    // 카테고리 삭제 페이지 (GET)
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteCategoryGET(@RequestParam("categoryId") int categoryId, Model model) throws Exception {
        // 삭제할 카테고리 정보 가져오기
        CategoryVO category = categoryService.getCategoryWithParents(categoryId);
        model.addAttribute("category", category);

        return "category/delete";
    }

    // 카테고리 삭제 처리 (POST)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteCategoryPOST(@RequestParam("categoryId") int categoryId) throws Exception {
        // 카테고리 삭제 처리
        categoryService.deleteCategory(categoryId);

        return "redirect:/category/list";
    }
}
