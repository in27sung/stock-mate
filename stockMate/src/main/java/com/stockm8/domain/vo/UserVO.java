package com.stockm8.domain.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserVO {
    // UserDTO (Data Transfer Object)
    // UserVO (Value Object)

    private Long userId;           // 사용자 고유 ID
    private String email;          // 사용자 이메일(로그인 ID)
    private String password;       // 사용자 비밀번호
    private String name;           // 사용자 이름
    private String role;           // 사용자 역할 (admin, manager, staff)
    private Integer businessId;    // 사업자 고유 ID (NULL 가능)
    private String telNumber;      // 사용자 전화번호
    private Timestamp createdAt;   // 계정 생성 날짜
    private Integer createdBy;     // 계정을 승인한 사용자 ID
    private Timestamp updatedAt;   // 계정 수정 날짜
    private String status;         // 계정 상태 (approved, pending, rejected)
    private Boolean isDeleted;     // 삭제 여부 (true/false)

}

