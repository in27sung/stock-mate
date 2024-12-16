package com.stockm8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stockm8.domain.vo.OrderItemVO;
import com.stockm8.domain.vo.OrderVO;
import com.stockm8.domain.vo.OrderVO.OrderType;
import com.stockm8.domain.vo.StockVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.service.OrderService;
import com.stockm8.service.UserService;

@Controller
@RequestMapping(value = "/order/*")
public class OrderController {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    
    @Inject
    private OrderService orderService;
    @Inject
    private UserService userService;
    
    /**
     * 주문 등록 페이지 표시(GET)
     * http://localhost:8088/order/register
     * @param model Spring MVC 모델 객체
     * @return 주문 등록 페이지의 뷰 이름
     * 
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String orderRegisterGET(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("orderRegisterGET() 호출");
        
        
	    // 세션에서 userId 가져오기
	    HttpSession session = request.getSession(false);
	    Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
        
	    // userId로 사용자 정보 조회
	    UserVO user = userService.getUserById(userId);
	    int businessId = user.getBusinessId();
        
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            Long userId = (Long) session.getAttribute("userId");
//            if (userId != null) {
//                UserVO user = userService.getUserById(userId);
//                if (user != null) {
//                    model.addAttribute("businessId", user.getBusinessId());
//                    // 추가적인 권한 체크나 비즈니스 로직
//                    return "order/register";
//                }
//            }
//        }
//        // 인증 실패 처리 => 음 메세지 ??? 어떻게 ??
//        return "user/main";
	    
	    return "order/register";
    }

    /**
     * 주문 등록 처리(POST)
     * http://localhost:8088/order/register
     * @param order 클라이언트에서 전송된 주문 정보
     * @return 처리 결과를 담은 Map 객체
     * @throws Exception 주문 처리 중 발생하는 예외
     * 
     */
    @RequestMapping(value = "/register", 
                   method = RequestMethod.POST,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> orderRegisterPOST(@RequestBody OrderVO order) throws Exception {
        logger.info("orderRegisterPOST() 호출");
        logger.info("주문 정보: " + order);

        // orderType 유효성 검사 추가(수주인지 / 발주인지 주문유형)
        if (order.getOrderType() == null) {
            throw new IllegalArgumentException("주문 유형이 누락되었습니다.");
        }
        
        
        // 주문에 orderItems가 있는지 확인(유효성 검사) 
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 누락되었습니다.");
        }
        
        // 주문 항목별 재고 검증 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        for (OrderItemVO item : order.getOrderItems()) {
            if (order.getOrderType() == OrderType.INBOUND) {
                // 수주의 경우 가용 재고 체크
                if (!orderService.checkAvailableStock(item)) {
                    throw new IllegalArgumentException(
                        String.format("재고 부족 - StockId: %d, 요청수량: %d", 
                            item.getStockId(), item.getQuantity())
                    );
                }
            }
        }
        
        // 주문번호 생성 및 설정
        String orderNumber = orderService.generateOrderNumber();
        order.setOrderNumber(orderNumber);
        
        // 첫 번째 주문 항목 가져오기
        OrderItemVO orderItem = order.getOrderItems().get(0);
        
        // 주문 처리
        orderService.insertOrderWithItems(order, order.getOrderItems());
        
        // 응답 생성
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "주문이 성공적으로 등록되었습니다.");
        response.put("orderNumber", orderNumber);
        
        return response;
    }
    
    /**
     * 가용 재고 목록 조회
     * http://localhost:8088/order/findAvailableStocks
     */
    @RequestMapping(value = "/findAvailableStocks",
                   method = RequestMethod.GET,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<StockVO> findAvailableStocks(HttpServletRequest request) throws Exception {
        logger.info("findAvailableStocks() 호출");
        
        // 세션에서 userId 가져오기
        HttpSession session = request.getSession(false);
        Long userId = (session != null) ? (Long)session.getAttribute("userId") : null;
        
        // userId로 사용자 정보 조회
        UserVO user = userService.getUserById(userId);
        int businessId = user.getBusinessId();
        
        return orderService.findAvailableStocks(businessId);
    }
    
    /**
     * 주문번호 생성
     * http://localhost:8088/order/generateOrderNumber
     */
    @RequestMapping(value = "/generateOrderNumber",
    				method = RequestMethod.GET,
    				produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String generateOrderNumber() throws Exception {
        logger.info("generateOrderNumber() 호출");
        return orderService.generateOrderNumber();
    }
    
    /**
     * 주문 목록 조회
     * http://localhost:8088/order/orderList
     */
    @RequestMapping(value = "/orderList" , method = RequestMethod.GET)
    public String orderListGET(Model model) {
    	logger.info("orderListGET() 호출");
    	
    	// main에서 넘어올떄 정달정보 ?? 로그인???세션 ??
    	
    	//서비스 -> DAO(주문 목록)
    	List<OrderVO> orderList = orderService.getOrderList();
    	
    	//뷰 페이지 정보 전달(model)
    	
    	model.addAttribute("orderList",orderList);
    	
    	return "/order/orderList";
    }
    
} //OrderController