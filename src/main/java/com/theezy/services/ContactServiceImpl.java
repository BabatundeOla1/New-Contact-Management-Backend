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
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
class ContactServiceImpl implements ContactService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    @CachePut(value = "contacts", key = "#p0")
    public ContactResponse saveContact(@Param("userId") String userId, ContactRequest contactRequest) {
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
    @CacheEvict(value = "contacts", key = "#userId")
    public void deleteAllContact(String userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        List<Contact> contactsToDelete = user.getContacts();
        contactRepository.deleteAll(contactsToDelete);

        user.getContacts().clear();
        userRepository.save(user);
    }

    @Override
    @Cacheable(value = "contacts", key = "#userId")
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
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));
    }

    @Override
    public ContactResponse blockContactByPhoneNumber(String userId, String phoneNumber) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        Contact foundContact =  contactRepository.findContactByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));

        if (user.getContacts().stream().noneMatch(myContact -> myContact.getId().equals(foundContact.getId()))){
            throw new ContactNotFoundException("Contact not found in your contact list.");
        }

        if (user.getContacts().stream().anyMatch(contact -> contact.getId().equals(foundContact.getId()))){
            foundContact.setBlocked(true);
            userRepository.save(user);
        }

        foundContact.setBlocked(true);
        contactRepository.save(foundContact);

        return ContactMapper.mapContactToResponse(foundContact);
    }

    @Override
    @CachePut(value = "contacts", key = "#userId")
    public List<Contact> getBlockedContacts(String userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        return user.getContacts().stream()
                .filter(Contact::isBlocked)
                .toList();
    }

    @Override
    @CacheEvict(value = "contacts", key = "#userId")
    public ContactResponse unblockContactByPhoneNumber(String userId, String phoneNumber) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id not found"));

        Contact foundContact =  contactRepository.findContactByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));

        if (user.getContacts().stream().noneMatch((myContact) -> myContact.getId().equals(foundContact.getId()))){
            throw new ContactNotFoundException("This contact does not belong to the user.");
        }
        foundContact.setBlocked(false);
        contactRepository.save(foundContact);

        return ContactMapper.mapContactToResponse(foundContact);
    }
}
