package com.theezy.data.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtpVerification {
    private String id;
    private String email;
    private String code;
    private LocalDateTime expirationTime;
}
