package com.theezy.dto.responses;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
public class ContactResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private String contactId;
    private Object data;
}
