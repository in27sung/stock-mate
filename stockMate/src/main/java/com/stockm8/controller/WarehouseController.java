package com.stockm8.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stockm8.domain.dto.WarehouseDetailDTO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.domain.vo.WarehouseVO;
import com.stockm8.service.UserService;
import com.stockm8.service.WarehouseService;

@Controller
@RequestMapping(value = "/warehouse/*") // 공통 URI 주소
public class WarehouseController {

	private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);
	
	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
	private UserService userService;
	
	// http://localhost:8088/warehouse/register
    /**
     * 창고 등록 페이지(GET)
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String warehouseRegistGET(Model model, HttpSession session) throws Exception {
        logger.info("warehouseRegistGET() 호출");

        // 세션에서 userId 가져오기
        Long userId = (session != null) ? (Long) session.getAttribute("userId") : null;

        // userId로 사용자 정보 조회
        UserVO user = userService.getUserById(userId);
        int businessId = user.getBusinessId();

        // 모델에 businessId 전달
        model.addAttribute("businessId", businessId);

        logger.info("창고 등록 페이지로 이동");
        return "warehouse/register"; // JSP 페이지 이름
    }
    
    /**
     * 창고 등록 처리
     * @param warehouse WarehouseVO 객체
     * @param request HttpServletRequest (세션 사용)
     * @return 성공 여부와 메시지 반환
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerWarehouse(@RequestBody WarehouseVO warehouse, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            HttpSession session = request.getSession(false);
            Long userId = (session != null) ? (Long) session.getAttribute("userId") : null;

            if (userId == null) {
                throw new IllegalArgumentException("로그인 상태가 아닙니다. 다시 로그인해주세요.");
            }

            UserVO user = userService.getUserById(userId);
            warehouse.setBusinessId(user.getBusinessId());
            warehouse.setManagerId(user.getUserId());

            warehouseService.registerWarehouse(warehouse);
            response.put("success", true);
            response.put("message", "창고 등록이 성공적으로 완료되었습니다.");
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            response.put("success", false);
            response.put("message", "같은 이름의 창고가 이미 등록되어 있습니다.");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "창고 등록 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
	// http://localhost:8088/warehouse/detail?warehouseId=12
    /**
     * 창고 상세 정보 페이지(GET)
     * @throws Exception 
     */
    @GetMapping("/detail")
    public String getWarehouseDetail(@RequestParam int warehouseId, HttpSession session, Model model) throws Exception {
        
    	// 세션에서 userId 가져오기
    	Long userId = (Long) session.getAttribute("userId");
        
    	// userId로 사용자 정보 조회
        UserVO user = userService.getUserById(userId);
        int businessId = user.getBusinessId();

        WarehouseDetailDTO warehouseDetail = warehouseService.getWarehouseDetail(warehouseId, businessId);

        model.addAttribute("warehouseDetail", warehouseDetail);
        
        return "warehouse/detail";
    }
}
