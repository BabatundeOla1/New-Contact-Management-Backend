package com.theezy.services;

import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.ContactResponse;
import com.theezy.dto.responses.UserRegisterResponse;

public interface ContactService {
    ContactResponse saveContact(ContactRequest contactRequest);
}
