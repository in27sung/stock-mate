package com.stockm8.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.stockm8.domain.dto.UpdateUserStatusDTO;
import com.stockm8.service.UserService;

@RestController
@RequestMapping("/api/manager")
public class ManagerApiController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerApiController.class);
	
    @Autowired
    private UserService userService;
    
    @PostMapping("/approve")
    public ResponseEntity<Map<String, Object>> approveStaff(
            @RequestBody UpdateUserStatusDTO updateUserStatusDTO,
            @SessionAttribute("userId") Long sessionUserId) throws Exception {
        
        Map<String, Object> response = new HashMap<>();

        // 세션에서 Manager ID를 DTO에 설정
        updateUserStatusDTO.setUserId(sessionUserId);

        // 입력값 검증
        if (updateUserStatusDTO.getApprovedUserId() == null || updateUserStatusDTO.getUserStatus() == null) {
            response.put("success", false);
            response.put("message", "유효하지 않은 데이터입니다.");
            return ResponseEntity.badRequest().body(response);
        }

        // 직원 승인 처리
        userService.updateUserStatus(updateUserStatusDTO);

        response.put("success", true);
        response.put("message", "직원이 성공적으로 승인되었습니다.");
        return ResponseEntity.ok(response);
    }
}
