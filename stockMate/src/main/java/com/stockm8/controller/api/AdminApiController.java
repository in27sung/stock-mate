package com.stockm8.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.stockm8.domain.dto.UpdateUserStatusDTO;
import com.stockm8.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminApiController.class);

    @Inject
    private UserService userService;

    @PostMapping("/approve")
    public ResponseEntity<Map<String, Object>> adminApprove(
            @RequestBody UpdateUserStatusDTO updateUserStatusDTO,
            @SessionAttribute(value = "userId", required = false) Long sessionUserId) {
        
        Map<String, Object> response = new HashMap<>();

        // 세션 값 확인
        if (sessionUserId == null) {
            logger.error("Session userId is missing");
            response.put("success", false);
            response.put("message", "세션 정보가 없습니다. 다시 로그인해주세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 세션에서 userId를 DTO에 설정
        updateUserStatusDTO.setUserId(sessionUserId);

        // 입력값 검증
        if (updateUserStatusDTO.getApprovedUserId() == null || updateUserStatusDTO.getUserStatus() == null) {
            logger.error("Invalid input: approvedUserId or userStatus is null - DTO: {}", updateUserStatusDTO);
            response.put("success", false);
            response.put("message", "유효하지 않은 데이터입니다.");
            return ResponseEntity.badRequest().body(response);
        }

        // 디버깅용 로그
//        logger.info("adminApprove() 호출 - approvedUserId: {}, userStatus: {}, userId: {}", 
//                    updateUserStatusDTO.getApprovedUserId(), 
//                    updateUserStatusDTO.getUserStatus(), 
//                    updateUserStatusDTO.getUserId());

        try {
            // 사용자 상태 변경
            userService.updateUserStatus(updateUserStatusDTO);

            // 성공 응답
            response.put("success", true);
            response.put("message", "상태가 성공적으로 업데이트되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외 처리
            logger.error("사용자 상태 업데이트 실패", e);
            response.put("success", false);
            response.put("message", "상태 업데이트 실패. 관리자에게 문의해주세요.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
