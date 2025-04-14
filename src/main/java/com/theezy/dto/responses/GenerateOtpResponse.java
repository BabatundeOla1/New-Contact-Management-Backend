package com.theezy.dto.responses;

import java.time.LocalDateTime;

public class GenerateOtpResponse {
    private String otpCode;
    private LocalDateTime expirationTime;
    private boolean isUsed;
}
