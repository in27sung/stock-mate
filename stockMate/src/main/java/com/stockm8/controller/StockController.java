package com.stockm8.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.domain.vo.WarehouseVO;
import com.stockm8.persistence.FilterCriteria;
import com.stockm8.service.StockService;
import com.stockm8.service.UserService;
import com.stockm8.service.WarehouseService;

@Controller
@RequestMapping("/stock")
public class StockController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Inject
    private StockService stockService;

    @Inject
    private UserService userService;

    @Inject
    private WarehouseService warehouseService;

    // 재고 등록 페이지
    // http://localhost:8088/stock/register
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String stockRegisterGET(@SessionAttribute("userId") Long userId, Model model) throws Exception {
        logger.info(" stockRegisterGET() 호출 ");

        // 사용자 정보 가져오기
        UserVO user = userService.getUserById(userId);
        int businessId = user.getBusinessId();
        logger.info("Business ID for userId {}: {}", userId, businessId);

        // 모델에 데이터 추가
        model.addAttribute("businessId", businessId);

        return "/stock/register";
    }

	// 재고 등록 처리
	@RequestMapping (value ="/register", method = RequestMethod.POST)
	public String registerStockPost(@ModelAttribute StockVO stock, 
									@SessionAttribute("userId") 
									Long userId, Model model) throws Exception {
		logger.info("registerStockPost() 실행");
		
		// 사용자 정보 가져오기
		UserVO user = userService.getUserById(userId);
		int businessId = user.getBusinessId();

		// StockVO에 추가 데이터 설정
		stock.setBusinessId(businessId);
		stock.setReservedQuantity(0); // 초기 예약 수량은 0
		stock.setAvailableStock(stock.getTotalQuantity()); // 초기 재고 = 입력 수량

		// 재고 등록 서비스 호출
		stockService.registerStock(stock);
		logger.info("Stock registered successfully: {}", stock);
		
		model.addAttribute("success", "재고 등록이 성공적으로 완료되었습니다.");
		return "redirect:/stock/list"; // 재고 목록 페이지로 이동
	}
	
	// 재고 목록 조회
	// http://localhost:8088/stock/list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getStockList(@SessionAttribute("userId") Long userId, Model model,
            				   @RequestParam(required = false) Integer warehouseId,
					           @RequestParam(required = false) String categoryName,
					           @RequestParam(required = false) Integer minStock,
					           @RequestParam(required = false) Integer maxStock,
					           @RequestParam(required = false, defaultValue = "desc") String sortOrder) throws Exception {
	    logger.info("getStockList 호출");

	    // 사용자 정보 조회
	    UserVO user = userService.getUserById(userId);
	    int businessId = user.getBusinessId();

	    // 창고 목록 조회
	    List<WarehouseVO> warehouseList = warehouseService.getWarehousesByBusinessId(businessId);
	    logger.info("조회된 창고 목록: {}", warehouseList.size());

	    // 창고명과 카테고리명 정보 제공
	    model.addAttribute("warehouseList", warehouseList);
	    model.addAttribute("categoryList", stockService.getCategoryList());

	    // 재고 리스트 조회
	    FilterCriteria criteria = new FilterCriteria(warehouseId, categoryName, minStock, maxStock, sortOrder, businessId);
	    List<StockVO> stockList = stockService.getStockList(criteria, sortOrder);
	    model.addAttribute("stockList", stockList);

	    // 필터링된 파라미터들을 모델에 추가
	    model.addAttribute("warehouseId", warehouseId);
	    model.addAttribute("categoryName", categoryName);
	    model.addAttribute("minStock", minStock);
	    model.addAttribute("maxStock", maxStock);
	    model.addAttribute("sortOrder", sortOrder);

	    return "/stock/list";
	}
}
