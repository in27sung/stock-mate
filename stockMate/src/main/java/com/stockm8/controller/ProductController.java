package com.stockm8.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stockm8.domain.vo.CategoryVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.CategoryService;
import com.stockm8.service.ProductService;
import com.stockm8.service.UserService;

@Controller
@RequestMapping(value = "/product") // 공통 URI 주소
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;

	// http://localhost:8088/product/register
	// 상품 등록 페이지
	@GetMapping("/register")
	public String registGET(Model model, HttpServletRequest request) throws Exception {
		logger.info("registGET() 호출");
		
	    // 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
        
	    // userId로 사용자 정보 조회
	    UserVO user = userService.getUserById(userId);
	    int businessId = user.getBusinessId();
	    
        // DB에서 카테고리 정보 가져오기
        List<CategoryVO> categoryList = categoryService.getCategoriesByBusinessId(businessId);

        // 모델에 데이터 추가
        model.addAttribute("categoryList", categoryList);

        // 창고 정보와 카테고리 정보가 없으면 추가 버튼 표시를 위한 플래그 설정
        model.addAttribute("hasCategories", !categoryList.isEmpty());

		logger.info("연결된 뷰페이지(/product/register.jsp) 이동");
		return "product/register"; // 상품 등록 페이지로 이동
	}

	// 상품 등록 처리
	@PostMapping("/register")
	public String registPOST(ProductVO product, 
							 HttpServletRequest request, 
							 RedirectAttributes rttr) throws Exception {
		logger.info("registPOST() 호출");

		// 전달정보(파라미터) 확인
		logger.info("product: {}", product);
	    
		// 세션에서 userId 가져오기 
		HttpSession session = request.getSession(false);
		Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
		
        // userId로 사용자 정보 조회
        UserVO user = userService.getUserById(userId);
        
        // user의 businessId 설정
        product.setBusinessId(user.getBusinessId());
				
		// DB등록처리 / Service -> DAO -> mapper(sql 호출) 
		productService.registerProduct(product);
		
		// DB insert 후 생성된 productId PK
		int productId = product.getProductId();
		
		logger.info("연결된 뷰페이지(/product/list.jsp) 이동");
		return "redirect/product/detail?productId=" + productId;
	}
	
	// 상품 상세 정보 페이지
	@GetMapping("/detail")
	public String detail(@RequestParam("productId") int productId, Model model) throws Exception {
		// 상품 상세 정보 조회 
		ProductVO product = productService.getProductByID(productId);
		
        // 뷰(JSP)에서 EL로 접근할 수 있도록 Model에 상품 정보 등록
        model.addAttribute("product", product);
		
		
		logger.info("연결된 뷰페이지(/product/detail.jsp) 이동");
		return "product/detail";
	}

}