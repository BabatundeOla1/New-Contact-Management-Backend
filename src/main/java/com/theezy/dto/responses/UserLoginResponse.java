package com.theezy.dto.responses;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String message;
    private String refreshToken;
    private String accessToken;
}
