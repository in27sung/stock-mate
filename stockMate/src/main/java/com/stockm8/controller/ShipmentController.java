package com.stockm8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.PageVO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.ReceivingShipmentVO;
import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.ReceivingService;
import com.stockm8.service.ShipmentService;
import com.stockm8.service.UserService;

@Controller
@RequestMapping(value = "/shipment/*")
public class ShipmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentController.class);

	@Inject
	private ShipmentService sService;
	
	@Inject
	private UserService uService;
		
	// http://localhost:8088/shipment/main
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void mainGET(Model model, HttpServletRequest request) throws Exception {
		logger.info("mainGET() 호출");
		
		// 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();
		
		List<ReceivingShipmentVO> ShipmentList = sService.getShipmentList(businessId);
		logger.info(ShipmentList.size() + "개");
		
		List<ReceivingShipmentVO> YesterdayShipmentList = sService.getYesterdayShipmentList(businessId);
		logger.info(YesterdayShipmentList.size() + "개");
		
		List<ReceivingShipmentVO> TDBYShipmentList = sService.getTDBYShipmentList(businessId);
		logger.info(TDBYShipmentList.size() + "개");
		
		model.addAttribute("ShipmentList", ShipmentList);
		model.addAttribute("YesterdayShipmentList", YesterdayShipmentList);
		model.addAttribute("TDBYShipmentList", TDBYShipmentList);
	}
	
	// http://localhost:8088/shipment/history
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public void historyGET(@RequestParam(value = "startDate", required = false) String startDate,
	                       @RequestParam(value = "endDate", required = false) String endDate,
	                       @RequestParam(value = "keyword", required = false) String keyword,
	                       Criteria cri, HttpServletRequest request,
	                       Model model) throws Exception {
	    logger.info("historyGET() 호출");
	    
	    // 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();

	    List<ReceivingShipmentVO> ShipmentList;
	    
	    int totalCount = 0;
	    
	    ShipmentList = sService.getShipmentHistoryList(cri, businessId);
        totalCount = sService.getTotalCount(businessId); // 전체 개수
	        
	    PageVO pageVO = new PageVO();
        pageVO.setCri(cri);
        pageVO.setTotalCount(totalCount);
        model.addAttribute("pageVO", pageVO);
		
	    logger.info(ShipmentList.size() + "개");
	    model.addAttribute("ShipmentList", ShipmentList);
	}
	
	// http://localhost:8088/shipment/search
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public void searchGET(@RequestParam(value = "startDate", required = false) String startDate,
	                      @RequestParam(value = "endDate", required = false) String endDate,
	                      @RequestParam(value = "keyword", required = false) String keyword,
	                      Criteria cri, HttpServletRequest request,
	                      Model model) throws Exception {
	    logger.info("searchGET() 호출");

	    List<ReceivingShipmentVO> ShipmentList;
	    
	    // 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();
	    
	    int totalCount = 0;
	    
	    // 날짜와 키워드가 모두 있는 경우
	    if (startDate != null && endDate != null && keyword != null) {
	        ShipmentList = sService.getHistoryByDateRange(startDate, endDate, keyword, cri, businessId);
	        totalCount = sService.getTotalCountBySearch(startDate, endDate, keyword, businessId);
	        
	    } else if (keyword != null) {
	        ShipmentList = sService.getHistoryByDateRange(null, null, keyword, cri, businessId);
	        totalCount = sService.getTotalCountBySearch(null, null, keyword, businessId);
	       
	    } else if (startDate != null && endDate != null) {
	        ShipmentList = sService.getHistoryByDateRange(startDate, endDate, null, cri, businessId);
	        totalCount = sService.getTotalCountBySearch(startDate, endDate, null, businessId);
	        
	    } else {
	        ShipmentList = sService.getShipmentHistoryList(cri, businessId);
	        totalCount = sService.getTotalCount(businessId); // 전체 개수
	        
	    }
	    
	    PageVO pageVO = new PageVO();
        pageVO.setCri(cri);
        pageVO.setTotalCount(totalCount);
        model.addAttribute("pageVO", pageVO);
		
	    logger.info(ShipmentList.size() + "개");
	    model.addAttribute("ShipmentList", ShipmentList);
	}
	
	// 새로고침
	@RequestMapping(value = "/insert1", method = RequestMethod.POST)
	public String insert1POST(HttpServletRequest request) throws Exception {
		logger.info("insertPOST() 호출");
		
		// 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();
		
		sService.insertShipment(businessId);
		
		return "redirect:/shipment/main";
	}
	
	// 새로고침
	@RequestMapping(value = "/insert2", method = RequestMethod.POST)
	public String insert2POST(HttpServletRequest request) throws Exception {
		logger.info("insertPOST() 호출");
		
		// 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();
		
		sService.insertShipment(businessId);
		
		return "redirect:/shipment/history";
	}	
	
	// 새로고침
	@RequestMapping(value = "/insert3", method = RequestMethod.POST)
	public String insert3POST(HttpServletRequest request) throws Exception {
		logger.info("insertPOST() 호출");
		
		// 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();
		
		sService.insertShipment(businessId);
		
		return "redirect:/shipment/search";
	}
	
	// http://localhost:8088/shipment/scan
	@RequestMapping(value = "/scan", method = RequestMethod.GET)
	public void scanGET(HttpServletRequest request, Model model,
			@RequestParam(value = "receivingShipmentNo", required = false) Integer receivingShipmentNo) throws Exception {
		logger.info("scanGET() 호출");
		
		// 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();
        
	    List<ReceivingShipmentVO> rsn = sService.getReceivingShipmentNo(businessId, receivingShipmentNo);
	    
	    model.addAttribute("rsn", rsn);
	}
	
	// http://localhost:8088/shipment/scan
	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> scanPOST(@RequestBody Map<String, String> bar, Model model, 
											HttpServletRequest request) throws Exception {
		logger.info("scanPOST() 호출");
        
        // 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();
	    
	    // QR 코드 데이터 처리 로직 (예: 데이터 저장, 검증 등)
 		String barcode = bar.get("barcode");
        Map<String, Object> response = new HashMap();
	    
	    if (userId == null) {
	        response.put("success", false);
	        response.put("message", "로그인 정보가 없습니다.");
	        return response;
	    }

	    try {
	    	sService.ShipmentStatusToComplete(businessId, barcode);
            int remainingStock = sService.increseStockByBarcode(businessId, barcode);
            int reservedQuantity = sService.decreaseReservedQuantity(businessId, barcode);
            ProductVO product = sService.productNameBarcode(businessId, barcode);
            if (remainingStock >= 0) {
                response.put("success", true);
                response.put("remainingStock", remainingStock);
                response.put("reservedQuantity", reservedQuantity);
                response.put("productName", product.getProductName());
                response.put("productPrice", product.getProductPrice());
            } else {
                response.put("success", false);
                response.put("message", "유효하지 않은 바코드입니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
	
	// http://localhost:8088/shipment/allScan
	@RequestMapping(value = "/allScan", method = RequestMethod.GET)
	public String allScanGET(HttpServletRequest request) throws Exception {
		logger.info("allScanGET() 호출");
		
		// 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
	    
	    // userId로 사용자 정보 조회
	    UserVO user = uService.getUserById(userId);
	    int businessId = user.getBusinessId();
	    
	    
        
        return "/shipment/allScan";
	}

   

	

} // ShipmentController end