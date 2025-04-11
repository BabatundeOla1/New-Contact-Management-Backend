package com.theezy.services;

import com.theezy.data.repositories.ContactRepository;
import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.responses.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
class ContactServiceImpl implements ContactService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ContactResponse saveContact(ContactRequest contactRequest) {
        return null;
    }
}
