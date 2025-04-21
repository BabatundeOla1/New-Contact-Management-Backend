package com.theezy.controller;

import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.requests.PhoneNumberRequest;
import com.theezy.dto.responses.ContactResponse;
import com.theezy.dto.responses.DeleteAllContactResponse;
import com.theezy.services.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts/")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("addcontact/user/{userId}/contacts")
    public ResponseEntity<ContactResponse> addContact(@PathVariable("userId") String userId,
                                                      @RequestBody ContactRequest contactRequest){

        return new ResponseEntity<>(contactService.saveContact(userId, contactRequest), HttpStatus.OK);
    }
    @DeleteMapping("deleteContact/user/{userId}/contacts/{contactId}")
    public ResponseEntity<ContactResponse> deleteContact(
            @PathVariable("userId") @NotBlank(message = "User ID is required") String userId,
            @PathVariable("contactId") @NotBlank(message = "Contact ID is required") String contactId) {

        return new ResponseEntity<>(contactService.deleteContactById(userId, contactId), HttpStatus.OK);
    }

    @DeleteMapping("deleteAllContact/user/{userId}/contacts")
    public ResponseEntity<DeleteAllContactResponse> deleteAllContact(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(contactService.deleteAllContact(userId), HttpStatus.OK);
    }

    @PostMapping("blockContact/user/{userId}/contacts")
    public ResponseEntity<ContactResponse> blockContact(@PathVariable("userId") String userId, @RequestBody @Valid PhoneNumberRequest request) {
        return new ResponseEntity<>(contactService.blockContactByPhoneNumber(userId, request.getPhoneNumber()), HttpStatus.OK);
    }

    @PostMapping("unblockContact/user/{userId}/contacts")
    public ResponseEntity<ContactResponse> unblockContact(@PathVariable("userId") String userId, @RequestBody @Valid PhoneNumberRequest request){
        return new ResponseEntity<>(contactService.unblockContactByPhoneNumber(userId, request.getPhoneNumber()), HttpStatus.OK);
    }

}
