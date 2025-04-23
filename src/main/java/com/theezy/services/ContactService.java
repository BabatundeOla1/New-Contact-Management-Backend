package com.theezy.services;

import com.theezy.data.models.Contact;
import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.requests.SearchContactByNameRequest;
import com.theezy.dto.requests.SearchContactByPhoneNumberRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.ContactResponse;
import com.theezy.dto.responses.DeleteAllContactResponse;
import com.theezy.dto.responses.UserRegisterResponse;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    ContactResponse saveContact(String userId, ContactRequest contactRequest);
    ContactResponse deleteContactById(String userId, String contactId);
    DeleteAllContactResponse deleteAllContact(String userId);
    List<Contact> getAllContacts(String userId);
    Contact searchContactByName(SearchContactByNameRequest searchContactByNameRequest);
    Contact searchContactByContactNumber(SearchContactByPhoneNumberRequest searchContactByPhoneNumberRequest);
    ContactResponse blockContactByPhoneNumber(String userId, String phoneNumber);

    List<Contact> getBlockedContacts(String userId);

    ContactResponse unblockContactByPhoneNumber(String userId, String phoneNumber);

    ContactResponse updateContact(String userId, String phoneNumber, ContactRequest updatedContactRequest);
}
