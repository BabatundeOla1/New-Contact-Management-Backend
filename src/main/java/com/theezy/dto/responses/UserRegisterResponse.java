package com.theezy.dto.responses;

import lombok.Data;

@Data
public class UserRegisterResponse {
    private String message;
    private String userId;
    private Object data;
}
