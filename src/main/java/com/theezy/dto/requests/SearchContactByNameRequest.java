package com.theezy.dto.requests;

import lombok.Data;

@Data
public class SearchContactByNameRequest {
    private String userId;
    private String name;
}
