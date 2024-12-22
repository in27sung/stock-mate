package com.stockm8.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.stockm8.domain.vo.CategoryVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.CategoryService;
import com.stockm8.service.UserService;

@RestController
@RequestMapping("/api/category")
public class categoryApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(categoryApiController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
    // 카테고리 등록 처리 (POST)
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerCategory(
    		@RequestBody CategoryVO categoryVO,
            @SessionAttribute("userId") Long userId) {
    	
        Map<String, Object> response = new HashMap<>();
        try {
            // 사용자 정보 가져오기
            UserVO user = userService.getUserById(userId);
            int businessId = user.getBusinessId();
            categoryVO.setBusinessId(businessId);

            // 카테고리 등록 (상위 카테고리 체크 포함)
            categoryService.registerCategoryWithParentCheck(categoryVO);

            response.put("success", true);
            response.put("message", "카테고리가 성공적으로 등록되었습니다.");
            
            logger.info("카테고리 등록 성공 - {}", categoryVO.getCategoryName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("카테고리 등록 실패 - 입력 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            logger.error("카테고리 등록 중 오류 발생: ", e);
            response.put("success", false);
            response.put("message", "카테고리 등록 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // http://localhost:8088/category/list
    // 카테고리 목록 페이지 호출 (GET)
    @GetMapping("/list")
    public ResponseEntity<List<CategoryVO>> getCategoryList(@SessionAttribute("userId") Long userId) throws Exception {
    	logger.info("listCategoryGET(Model model) 호출");
    	
        // 사용자 정보 가져오기
        UserVO user = userService.getUserById(userId);
        int businessId = user.getBusinessId();
        logger.info("Business ID for userId {}: {}", userId, businessId);
        
        // 카테고리 목록 조회
        List<CategoryVO> categories = categoryService.getCategoriesByBusinessId(businessId); 
        
        // JSP로 데이터 전달
        return ResponseEntity.ok(categories);
    }

}
