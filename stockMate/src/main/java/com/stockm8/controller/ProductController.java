package com.stockm8.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.WarehouseVO;
import com.stockm8.service.CategoryService;
import com.stockm8.service.ProductService;
import com.stockm8.service.UserService;
import com.stockm8.service.WarehouseService;

@Controller
@RequestMapping(value = "/product") // 공통 URI 주소
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

//	@Autowired
//	private WarehouseService warehouseService;

//	@Autowired
//	private CategoryService categoryService;

	// http://localhost:8088/product/register
	// 상품 등록 페이지로 이동
	@GetMapping("/register")
public String registGET(HttpSession session, Model model) throws Exception {

		logger.info("registGET() 호출");

	    // 세션에서 유저 ID 가져오기
        Long userId = (Long) session.getAttribute("userId");
        
//        if (userId == null) {
//            logger.warn("세션이 만료되었습니다. 로그인 페이지로 이동합니다.");
//            return "redirect:/user/login";
//        }
        // DB에서 창고 정보와 카테고리 정보 가져오기
        // 회사정보를 통한 warehouseID가져오기 여러중복된 businessID를 group by하여 
//        List<WarehouseVO> warehouses = warehouseService.getWarehousesByBusinessId(business);
//        List<CategoryVO> categories = categoryService.getCategoriesByBusinessId(business.getId());

        // 모델에 데이터 추가
//        model.addAttribute("warehouses", warehouses);
//        model.addAttribute("categories", categories);

        // 창고 정보와 카테고리 정보가 없으면 추가 버튼 표시를 위한 플래그 설정
//        model.addAttribute("hasWarehouses", !warehouses.isEmpty());
//        model.addAttribute("hasCategories", !categories.isEmpty());

		logger.info("연결된 뷰페이지(/board/register.jsp) 이동");
		return "product/register"; // 상품 등록 페이지로 이동
	}

	// 상품 등록
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(ProductVO pVO, RedirectAttributes rttr) throws Exception {
		logger.info("registPOST() 호출");

		// 전달정보(파라메터) 저장
		logger.info("pVO: {}", pVO);

		// Service -> DAO -> mapper(sql 호출)
		productService.registerProduct(pVO);

		// 정보 전달
		rttr.addFlashAttribute("result", "complete");

		logger.info("연결된 뷰페이지(/board/list.jsp) 이동");
		return "redireect/product/list";
	}

}