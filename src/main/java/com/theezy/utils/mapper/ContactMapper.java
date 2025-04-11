package com.theezy.utils.mapper;

import com.theezy.data.models.Contact;
import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.responses.ContactResponse;

public class ContactMapper {
    public static Contact mapRequestToContact(ContactRequest contactRequest){
        Contact contact = new Contact();
        contact.setName(contactRequest.getName());
        contact.setEmail(contactRequest.getEmail());
        contact.setAddress(contactRequest.getAddress());
        contact.setPhoneNumber(contactRequest.getPhoneNumber());
        contact.setBlocked(true);
        contact.setUserId(contactRequest.getUserId());
        return contact;
    }

    public static ContactResponse mapContactToResponse(String message){
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setMessage(message);
        return contactResponse;
    }
}
