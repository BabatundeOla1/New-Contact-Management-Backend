package com.theezy.dto.responses;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ContactResponse {
    private String message;
}
