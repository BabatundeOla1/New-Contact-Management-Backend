package com.theezy.dto.requests;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document
public class GenerateOtpRequest {
    private String id;
    private String otpCode;
    private LocalDateTime expirationTime;
    private boolean isUsed;
}
