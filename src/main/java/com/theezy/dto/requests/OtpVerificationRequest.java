package com.theezy.dto.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtpVerificationRequest {
    private String email;
    private String otp;
    private LocalDateTime expirationTime;
}
