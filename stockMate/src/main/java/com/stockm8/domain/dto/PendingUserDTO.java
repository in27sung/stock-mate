package com.stockm8.domain.dto;

import java.sql.Timestamp;

import com.stockm8.domain.enums.UserRole;
import com.stockm8.domain.enums.UserStatus;

import lombok.Data;

@Data
public class PendingUserDTO {

    // 사용자 정보
    private Long approvedUserId;
    private String email;
    private String userName;
    private UserRole userRole;
    private String telNumber;
    private Timestamp createdAt;
    private UserStatus userStatus;

    // 사업자 정보
    private Integer businessId;
    private String businessNumber;
    private String businessName;

}
