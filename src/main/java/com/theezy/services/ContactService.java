package com.theezy.services;

import com.theezy.data.models.Contact;
import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.ContactResponse;
import com.theezy.dto.responses.UserRegisterResponse;

import java.util.List;

public interface ContactService {
    ContactResponse saveContact(String userId, ContactRequest contactRequest);
    ContactResponse deleteContactById(String userId, String contactId);
    void deleteAllContact(String userId);

    List<Contact> getAllContacts(String userId);

    Contact searchContactByName(String userId, String name);
}
