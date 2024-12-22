package com.stockm8.domain.dto;

import com.stockm8.domain.enums.UserStatus;

import lombok.Data;

@Data
public class UpdateUserStatusDTO {

    private Long approvedUserId; // 승인받는 사용자 ID

    private UserStatus userStatus; // Enum으로 상태값 처리

    private Long userId; // 승인 작업을 수행하는 관리자 ID
}
