package com.theezy.services;

import com.theezy.data.models.Contact;
import com.theezy.data.models.User;
import com.theezy.data.repositories.ContactRepository;
import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.responses.ContactResponse;
import com.theezy.utils.exception.ContactNotFoundException;
import com.theezy.utils.exception.UserNotFoundException;
import com.theezy.utils.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ContactServiceImpl implements ContactService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ContactResponse saveContact(String userId, ContactRequest contactRequest) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        Contact mappedContact = ContactMapper.mapRequestToContact(contactRequest);

        contactRepository.save(mappedContact);
        user.getContacts().add(mappedContact);
        userRepository.save(user);

        return ContactMapper.mapContactToResponse(mappedContact);
    }

    @Override
    public ContactResponse deleteContactById(String userId,  String contactId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        Contact foundContact = contactRepository.findContactById(contactId)
                        .orElseThrow(()-> new ContactNotFoundException("Contact Id not found or already deleted"));

        boolean contactToDelete = user.getContacts().removeIf(contactToRemove -> contactToRemove.getId().equals(foundContact.getId()));
        if(!contactToDelete){
            throw new ContactNotFoundException("contact not found in user Contact list");
        }

        contactRepository.delete(foundContact);
        userRepository.save(user);

        return ContactMapper.mapContactToResponse(foundContact);
    }

    @Override
    public void deleteAllContact(String userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        user.getContacts().clear();
        userRepository.save(user);
    }

    @Override
    public List<Contact> getAllContacts(String userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        return user.getContacts();
    }

    @Override
    public Contact searchContactByName(String userId, String name) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        return contactRepository.findContactByName(name)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));
    }

    @Override
    public Contact searchContactByContactNumber(String userId, String phoneNumber) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        return contactRepository.findContactByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));    }
}
