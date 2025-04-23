package com.theezy.dto.requests;

import lombok.Data;

@Data
public class SearchContactByPhoneNumberRequest {
    private String userId;
    private String phoneNumber;
}
