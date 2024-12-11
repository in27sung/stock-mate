package com.stockm8.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.PageVO;
import com.stockm8.domain.vo.ReceivingShipmentVO;
import com.stockm8.service.ReceivingService;

@Controller
@RequestMapping(value = "/receiving/*")
public class ReceivingController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReceivingController.class);

	@Inject
	private ReceivingService rService;
	
	// http://localhost:8088/receiving/main
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void mainGET(Model model) throws Exception {
		logger.info("mainGET() 호출");
		
		List<ReceivingShipmentVO> ReceivingList = rService.getReceivingList();
		logger.info(ReceivingList.size() + "개");
		
		List<ReceivingShipmentVO> YesterdayReceivingList = rService.getYesterdayReceivingList();
		logger.info(YesterdayReceivingList.size() + "개");
		
		List<ReceivingShipmentVO> TDBYReceivingList = rService.getTDBYReceivingList();
		logger.info(TDBYReceivingList.size() + "개");
		
		model.addAttribute("ReceivingList", ReceivingList);
		model.addAttribute("YesterdayReceivingList", YesterdayReceivingList);
		model.addAttribute("TDBYReceivingList", TDBYReceivingList);
	}
	
	// http://localhost:8088/receiving/history
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public void historyGET(@RequestParam(value = "startDate", required = false) String startDate,
	                       @RequestParam(value = "endDate", required = false) String endDate,
	                       @RequestParam(value = "keyword", required = false) String keyword,
	                       Criteria cri,
	                       Model model) throws Exception {
	    logger.info("historyGET() 호출");

	    List<ReceivingShipmentVO> ReceivingList;
	    
	    int totalCount = 0;
	    
        ReceivingList = rService.getReceivingHistoryList(cri);
        totalCount = rService.getTotalCount(); // 전체 개수
	        
	    PageVO pageVO = new PageVO();
        pageVO.setCri(cri);
        pageVO.setTotalCount(totalCount);
        model.addAttribute("pageVO", pageVO);
		
	    logger.info(ReceivingList.size() + "개");
	    model.addAttribute("ReceivingList", ReceivingList);
	}
	
	// http://localhost:8088/receiving/search
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public void searchGET(@RequestParam(value = "startDate", required = false) String startDate,
	                      @RequestParam(value = "endDate", required = false) String endDate,
	                      @RequestParam(value = "keyword", required = false) String keyword,
	                      Criteria cri,
	                      Model model) throws Exception {
	    logger.info("searchGET() 호출");

	    List<ReceivingShipmentVO> ReceivingList;
	    
	    int totalCount = 0;
	    
	    // 날짜와 키워드가 모두 있는 경우
	    if (startDate != null && endDate != null && keyword != null) {
	        ReceivingList = rService.getHistoryByDateRange(startDate, endDate, keyword, cri);
	        totalCount = rService.getTotalCountBySearch(startDate, endDate, keyword);
	        
	    } else if (keyword != null) {
	        ReceivingList = rService.getHistoryByDateRange(null, null, keyword, cri);
	        totalCount = rService.getTotalCountBySearch(null, null, keyword);
	       
	    } else if (startDate != null && endDate != null) {
	        ReceivingList = rService.getHistoryByDateRange(startDate, endDate, null, cri);
	        totalCount = rService.getTotalCountBySearch(startDate, endDate, null);
	        
	    } else {
	        ReceivingList = rService.getReceivingHistoryList(cri);
	        totalCount = rService.getTotalCount(); // 전체 개수
	        
	    }
	    
	    PageVO pageVO = new PageVO();
        pageVO.setCri(cri);
        pageVO.setTotalCount(totalCount);
        model.addAttribute("pageVO", pageVO);
		
	    logger.info(ReceivingList.size() + "개");
	    model.addAttribute("ReceivingList", ReceivingList);
	}
	
	// 새로고침
	@RequestMapping(value = "/insert1", method = RequestMethod.POST)
	public String insert1POST() throws Exception {
		logger.info("insertPOST() 호출");
		
		rService.insertReceiving();
		
		return "redirect:/receiving/main";
	}
	
	// 새로고침
	@RequestMapping(value = "/insert2", method = RequestMethod.POST)
	public String insert2POST() throws Exception {
		logger.info("insertPOST() 호출");
		
		rService.insertReceiving();
		
		return "redirect:/receiving/history";
	}	
	
	// 새로고침
	@RequestMapping(value = "/insert3", method = RequestMethod.POST)
	public String insert3POST() throws Exception {
		logger.info("insertPOST() 호출");
		
		rService.insertReceiving();
		
		return "redirect:/receiving/search";
	}
	
	// http://localhost:8088/receiving/scanner
	@RequestMapping(value = "/scanner", method = RequestMethod.GET)
	public String scannerGET(@RequestParam(value = "qrData", required = false) String qrData, Model model) throws Exception {
		logger.info("scannerGET() 호출");
        // QR 코드 데이터 처리 로직 (예: 데이터 저장, 검증 등)
        logger.info("받은 QR 코드 데이터: " + qrData);

        // 처리 결과를 JSP로 전달
        model.addAttribute("qrData", qrData);	
        
        return "/receiving/scanner";
	}
	
	@RequestMapping(value = "/scanner", method = RequestMethod.POST)
	public String scannePOST(@RequestParam(value = "qrData", required = false) String qrData, Model model) throws Exception {
		logger.info("scannerGET() 호출");
        // QR 코드 데이터 처리 로직 (예: 데이터 저장, 검증 등)
        logger.info("받은 QR 코드 데이터: " + qrData);

        // 처리 결과를 JSP로 전달
        model.addAttribute("qrData", qrData);	
        
        return "/receiving/scanner";
	}


   

	

} // ReceivingController end