package com.stockm8.domain.vo;

import java.sql.Timestamp;

import com.stockm8.domain.enums.UserRole;
import com.stockm8.domain.enums.UserStatus;

import lombok.Data;

@Data
public class UserVO {
    // UserDTO (Data Transfer Object)
    // UserVO (Value Object)

    private Long userId;           // 사용자 고유 ID
    private String email;          // 사용자 이메일(로그인 ID)
    private String password;       // 사용자 비밀번호
    private String userName;       // 사용자 이름
    private UserRole userRole;     // 사용자 역할 (ADMIN, MANAGER, STAFF)
    private Integer businessId;    // 사업자 고유 ID (NULL 가능)
    private String telNumber;      // 사용자 전화번호
    private Integer createdBy;     // 계정을 승인한 사용자 ID
    private Timestamp createdAt;   // 계정 생성 날짜
    private Timestamp updatedAt;   // 계정 수정 날짜
    private UserStatus userStatus; // 계정 상태 (approved, pending, rejected)
    private Boolean isDeleted;     // 삭제 여부 (true/false)
}




