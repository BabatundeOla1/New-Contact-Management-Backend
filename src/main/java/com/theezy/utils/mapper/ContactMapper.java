package com.theezy.utils.mapper;

import com.theezy.data.models.Contact;
import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.responses.ContactResponse;

public class ContactMapper {
    public static Contact mapRequestToContact(ContactRequest contactRequest){
        Contact contact = new Contact();
        contact.setId(contactRequest.getId());
        contact.setName(contactRequest.getName());
        contact.setEmail(contactRequest.getEmail());
        contact.setAddress(contactRequest.getAddress());
        contact.setPhoneNumber(contactRequest.getPhoneNumber());
        contact.setBlocked(false);
        contact.setUserId(contactRequest.getUserId());
        return contact;
    }

    public static ContactResponse mapContactToResponse(Contact contact){
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setContactId(contact.getId());
        contactResponse.setMessage("Successful");
        return contactResponse;
    }
}
