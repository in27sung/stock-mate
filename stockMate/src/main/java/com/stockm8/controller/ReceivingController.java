package com.stockm8.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public void historyGET(Model model) throws Exception {
		logger.info("historyGET() 호출");
	}
	
	
	
	

} // ReceivingController end
