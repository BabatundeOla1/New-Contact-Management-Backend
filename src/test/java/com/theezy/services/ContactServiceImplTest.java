package com.theezy.services;

import com.theezy.data.models.Contact;
import com.theezy.data.models.User;
import com.theezy.data.repositories.ContactRepository;
import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.utils.mapper.ContactMapper;
import com.theezy.utils.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ContactServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactRepository contactRepository;

    @BeforeEach
    public void deleteAllUser(){
        userRepository.deleteAll();
    }
    @BeforeEach
    public void deleteAllContact(){
        contactRepository.deleteAll();
    }

    public void setUpUser(UserRegisterRequest userRegisterRequest){
        Contact contact = new Contact();
        contact.setEmail("Tunde@gmail.com");
        contact.setPhoneNumber("09022345678");
        contact.setName("You");
        contactRepository.save(contact);

        userRegisterRequest.setContact(contact);
        userRegisterRequest.setPassword("Babatunde123");
        userRegisterRequest.setFirstName("Tunde");
        userRegisterRequest.setLastName("Olaleye");
    }

    @Test
    public void testThatUserCanRegisterAndSaveContact(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpUser(userRegisterRequest);
        userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setName("New contact");
        contactRequest.setPhoneNumber("09011114419");
        contactRequest.setEmail("Newcontact@gmail.com");
        contactRequest.setUserId(userRegisterRequest.getId());
        contactRequest.setBlocked(false);
        contactRequest.setAddress("Bariga");

        Contact mappedContact = ContactMapper.mapRequestToContact(contactRequest);
        contactRepository.save(mappedContact);
        userRegisterRequest.getContacts().add(mappedContact);
        User mappedUser = UserMapper.mapRequestToUser(userRegisterRequest);
        userRepository.save(mappedUser);


        assertEquals(2, contactRepository.count());
        System.out.println(userRegisterRequest);
    }

    @Test
    public void testThatUserCanRegisterAndSaveMoreContacts(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpUser(userRegisterRequest);
        userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setName("New contact");
        contactRequest.setPhoneNumber("09011114419");
        contactRequest.setEmail("Newcontact@gmail.com");
        contactRequest.setUserId(userRegisterRequest.getId());
        contactRequest.setBlocked(false);
        contactRequest.setAddress("Bariga");

        Contact mappedContact = ContactMapper.mapRequestToContact(contactRequest);
        contactRepository.save(mappedContact);

        ContactRequest secondContact = new ContactRequest();
        secondContact.setName("Second contact");
        secondContact.setPhoneNumber("09012224419");
        secondContact.setEmail("Secondcontact@gmail.com");
        secondContact.setUserId(userRegisterRequest.getId());
        secondContact.setBlocked(false);
        secondContact.setAddress("Bariga");

        Contact secondMappedContact = ContactMapper.mapRequestToContact(secondContact);
        contactRepository.save(secondMappedContact);


        userRegisterRequest.getContacts().add(mappedContact);
        userRegisterRequest.getContacts().add(secondMappedContact);
        User mappedUser = UserMapper.mapRequestToUser(userRegisterRequest);
        userRepository.save(mappedUser);


        assertEquals(3, contactRepository.count());
        System.out.println(userRegisterRequest);
    }
}