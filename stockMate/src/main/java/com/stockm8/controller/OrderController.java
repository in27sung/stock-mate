package com.stockm8.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockm8.domain.enums.OrderStatus;
import com.stockm8.domain.vo.Criteria;
import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;
import com.stockm8.domain.vo.PageVO;
import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.OrderProcessor;
import com.stockm8.service.OrderService;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "/order/*")
public class OrderController {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    
    @Inject
    private OrderService orderService;
    
    @Inject
    private OrderProcessor orderProcessor;
    
    // 현재 로그인한 사용자 정보 가져오기(인터셉터에서 정의됨)
    private UserVO getCurrentUser(HttpServletRequest request) {
        return (UserVO) request.getAttribute("currentUser");
    }
    
    /**
     * 주문 등록 페이지 표시(GET)
     * http://localhost:8088/order/register
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGET(HttpServletRequest request) throws Exception {
        logger.info("orderRegisterGET() 호출");
        UserVO currentUser = getCurrentUser(request);
        int businessId = currentUser.getBusinessId();
        logger.info("Business ID for userId {}: {}", currentUser, businessId);

        return "order/register";
    }
    
    /**
     * 주문 등록 처리(POST)
     */
    @RequestMapping(value = "/register", 
                   method = RequestMethod.POST,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String registerPOST(@RequestBody OrderVO order, 
                           HttpServletRequest request) throws Exception {
        logger.info("orderRegisterPOST() 호출");
        logger.debug("주문 데이터: {}", order);
        
        UserVO currentUser = getCurrentUser(request);
        int businessId = currentUser.getBusinessId();
        Long userId = currentUser.getUserId();
	    
        
        // 주문 등록을 OrderProcessor에 위임
        Map<String, String> response = orderProcessor.process(order, businessId, userId);
        
        // Map을 JSON 문자열로 변환하여 반환
        ObjectMapper objectMapper = new ObjectMapper();
        
        return objectMapper.writeValueAsString(response);  // JSON 문자열로 변환하여 반환
        
    }
    
    /**
     * 주문번호 생성 API
     * http://localhost:8088/order/generateOrderNumber
     */
    @RequestMapping(value = "/generateOrderNumber",
                   method = RequestMethod.GET,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String generateOrderNumber() throws Exception {
        logger.info("generateOrderNumber() 호출");
        String orderNumber = orderService.generateOrderNumber();
        logger.debug("생성된 주문번호: {}", orderNumber);
        return orderNumber;
    }
    
    /**
     * 가용 재고 목록 조회 API
     * http://localhost:8088/order/stocks
     */
    @RequestMapping(value = "/findAvailableStocks", method = RequestMethod.GET)
    @ResponseBody
    public List<StockVO> getAvailableStocks(HttpServletRequest request) throws Exception {
        logger.info("getAvailableStocks() 호출");
        UserVO currentUser = getCurrentUser(request);
        logger.debug("사업자 ID: {}", currentUser.getBusinessId());
        return orderService.findAvailableStocks(currentUser.getBusinessId());
    }
    
    /**
     * 주문 목록 조회 페이지
     * http://localhost:8088/order/list
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public String listGET(@ModelAttribute Criteria cri, 
                         Model model, 
                         HttpServletRequest request) throws Exception {
        logger.info("orderListGET() 호출");
        logger.debug("페이지 정보: {}", cri);
        
        // 페이징 정보가 없는 경우 기본값 설정
        if (cri == null) {
            cri = new Criteria();
        }
        
        UserVO currentUser = getCurrentUser(request);
        int businessId = currentUser.getBusinessId();
        
        // 주문 목록 및 페이징 정보 조회
        List<OrderVO> orderList = orderService.getOrderList(cri, businessId);
        int totalCount = orderService.getTotalOrderCount(businessId);
        
        // PageVO 생성 및 설정 수정
        PageVO pageVO = new PageVO();
        pageVO.setCri(cri);
        pageVO.setTotalCount(totalCount);
        
        model.addAttribute("orderList", orderList);
        model.addAttribute("pageVO", pageVO);
        
        logger.debug("조회된 주문 수: {}", orderList.size());
        return "order/orderList";
    }
    
    /**
     * 주문 상세 정보 조회
     * http://localhost:8088/order/orderDetail?orderId=1
     */
    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)  // URL 수정
    public String orderDetailGET(@RequestParam("orderId") int orderId, Model model) throws Exception {
        logger.info("orderDetailGET() 호출 - orderId: {}", orderId);
        
        // 주문 상세 정보 조회
        OrderVO order = orderService.getOrderById(orderId);
        List<OrderItemVO> orderItems = orderService.getOrderItemsByOrderId(orderId);
        
        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
        
        logger.debug("주문 정보: {}", order);
        logger.debug("주문 항목 수: {}", orderItems.size());
        
        return "order/orderDetail";  // orderDetail.jsp로 이동
    }
    
} //OrderController