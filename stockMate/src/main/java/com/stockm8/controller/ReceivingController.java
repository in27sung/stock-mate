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
	
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String historyGET(@RequestParam(value = "startDate", required = false) String startDate,
	                         @RequestParam(value = "endDate", required = false) String endDate,
	                         @RequestParam(value = "keyword", required = false) String keyword,
	                         Criteria cri,
	                         Model model) throws Exception {
	    logger.info("historyGET() 호출");

	    List<ReceivingShipmentVO> ReceivingList;
	    
	    // 날짜와 키워드가 모두 있는 경우
	    if (startDate != null && endDate != null && keyword != null) {
	        ReceivingList = rService.getHistoryByDateRange(startDate, endDate, keyword, cri);
	        int totalCount = rService.getTotalCountBySearch(startDate, endDate, keyword, cri);
	        PageVO pageVO = new PageVO();
	        pageVO.setCri(cri);
	        pageVO.setTotalCount(totalCount); // 총 개수를 검색 조건에 맞게 계산
	        model.addAttribute("pageVO", pageVO);
	    } else if (keyword != null) {
	        ReceivingList = rService.getHistoryByDateRange(null, null, keyword, cri);
	        int totalCount = rService.getTotalCountBySearch(null, null, keyword, cri);
	        PageVO pageVO = new PageVO();
	        pageVO.setCri(cri);
	        pageVO.setTotalCount(totalCount);
	        model.addAttribute("pageVO", pageVO);
	    } else if (startDate != null && endDate != null) {
	        ReceivingList = rService.getHistoryByDateRange(startDate, endDate, null, cri);
	        int totalCount = rService.getTotalCountBySearch(startDate, endDate, null, cri);
	        PageVO pageVO = new PageVO();
	        pageVO.setCri(cri);
	        pageVO.setTotalCount(totalCount);
	        model.addAttribute("pageVO", pageVO);
	    } else {
	        ReceivingList = rService.getReceivingHistoryList(cri);
	        int totalCount = rService.getTotalCount(); // 전체 개수
	        PageVO pageVO = new PageVO();
	        pageVO.setCri(cri);
	        pageVO.setTotalCount(totalCount);
	        model.addAttribute("pageVO", pageVO);
	    }
		
	    logger.info(ReceivingList.size() + "개");
	    model.addAttribute("ReceivingList", ReceivingList);
	    
	    return "/receiving/history";
	}
	
	
	   

	

} // ReceivingController end
