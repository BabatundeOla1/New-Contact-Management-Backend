package com.theezy.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class OtpGenerator {

    private String id;
    private String code;
    private LocalDateTime expirationTime;
    private boolean isUsed;
}
