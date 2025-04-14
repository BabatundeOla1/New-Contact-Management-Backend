package com.theezy.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theezy.data.models.Contact;
import com.theezy.data.models.User;
import com.theezy.data.repositories.ContactRepository;
import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.ContactRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.ContactResponse;
import com.theezy.dto.responses.UserRegisterResponse;
import com.theezy.utils.mapper.ContactMapper;
import com.theezy.utils.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Autowired
    private ObjectMapper objectMapper;

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

    public void setUpContactToAdd(ContactRequest contactRequest){
        contactRequest.setName("Tunde");
        contactRequest.setPhoneNumber("09036011444");
        contactRequest.setEmail("Tunde@gmail.com");
        contactRequest.setBlocked(false);
        contactRequest.setAddress("Bariga");
    }
    public void setUpSecondContactToAdd(ContactRequest contactRequest){
        contactRequest.setName("Beejay");
        contactRequest.setPhoneNumber("09012345678");
        contactRequest.setEmail("Beejay@gmail.com");
        contactRequest.setBlocked(false);
        contactRequest.setAddress("Mushin");
    }
    @Test
    public void testThatUserCanRegisterAndSaveContact() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpUser(userRegisterRequest);
        UserRegisterResponse response = userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        ContactRequest firstContact = new ContactRequest();
        setUpContactToAdd(firstContact);

        contactService.saveContact(response.getUserId(), firstContact);
        assertNotNull(response.getUserId());
        assertEquals(2, contactRepository.count());
        try {
            String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userRegisterRequest);
            System.out.println(jsonOutput);
        }
        catch (Exception e){
            System.out.println("Error converting to JSON: " + e.getMessage());
        }
    }

    @Test
    public void testThatUserCanRegisterAndSaveMoreContacts() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpUser(userRegisterRequest);
        UserRegisterResponse response = userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        ContactRequest firstContact = new ContactRequest();
        setUpContactToAdd(firstContact);
        contactService.saveContact(response.getUserId(), firstContact);
        assertNotNull(response.getUserId());
        assertEquals(2, contactRepository.count());

        ContactRequest secondContact = new ContactRequest();
        setUpSecondContactToAdd(secondContact);
        contactService.saveContact(response.getUserId(), secondContact);
        assertEquals(3, contactRepository.count());
    }

    @Test
    public void testThatUserCanDeleteFromContactList(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpUser(userRegisterRequest);
        UserRegisterResponse response = userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        ContactRequest firstContact = new ContactRequest();
        setUpContactToAdd(firstContact);
        ContactResponse firstSavedContactResponse =  contactService.saveContact(response.getUserId(), firstContact);
        assertNotNull(response.getUserId());
        assertEquals(2, contactRepository.count());

        ContactRequest secondContact = new ContactRequest();
        setUpSecondContactToAdd(secondContact);
        ContactResponse secondSavedContactResponse = contactService.saveContact(response.getUserId(), secondContact);
        assertEquals(3, contactRepository.count());

        contactService.deleteContactById(response.getUserId(), secondSavedContactResponse.getContactId());

        User user = userRepository.findUserById(response.getUserId()).orElseThrow();

        assertEquals(1, user.getContacts().size());
        assertEquals(2, contactRepository.count());
    }

    @Test
    public void testThatUserCanDeleteAllContacts(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpUser(userRegisterRequest);
        UserRegisterResponse response = userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        ContactRequest firstContact = new ContactRequest();
        setUpContactToAdd(firstContact);
        ContactResponse firstSavedContactResponse =  contactService.saveContact(response.getUserId(), firstContact);
        assertNotNull(response.getUserId());
        assertEquals(2, contactRepository.count());

        ContactRequest secondContact = new ContactRequest();
        setUpSecondContactToAdd(secondContact);
        ContactResponse secondSavedContactResponse = contactService.saveContact(response.getUserId(), secondContact);
        assertEquals(3, contactRepository.count());

        contactService.deleteAllContact(response.getUserId());

        assertEquals(0, userRegisterRequest.getContacts().size());
    }
    @Test
    public void testThatUserCanViewAllContacts(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpUser(userRegisterRequest);
        UserRegisterResponse response = userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        ContactRequest firstContact = new ContactRequest();
        setUpContactToAdd(firstContact);
        ContactResponse firstSavedContactResponse =  contactService.saveContact(response.getUserId(), firstContact);
        assertNotNull(response.getUserId());
        assertEquals(2, contactRepository.count());

        ContactRequest secondContact = new ContactRequest();
        setUpSecondContactToAdd(secondContact);
        ContactResponse secondSavedContactResponse = contactService.saveContact(response.getUserId(), secondContact);
        assertEquals(3, contactRepository.count());

        List<Contact> allUserContacts =  contactService.getAllContacts(response.getUserId());

        assertEquals(2, allUserContacts.size());

        try {
            String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allUserContacts);
            System.out.println(jsonOutput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testThatUserCanSearchForA_ContactUsingName(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpUser(userRegisterRequest);
        UserRegisterResponse response = userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        ContactRequest firstContact = new ContactRequest();
        setUpContactToAdd(firstContact);
        ContactResponse firstSavedContactResponse =  contactService.saveContact(response.getUserId(), firstContact);
        assertNotNull(response.getUserId());
        assertEquals(2, contactRepository.count());

        ContactRequest secondContact = new ContactRequest();
        setUpSecondContactToAdd(secondContact);
        ContactResponse secondSavedContactResponse = contactService.saveContact(response.getUserId(), secondContact);
        assertEquals(3, contactRepository.count());

        Contact foundContact =  contactService.searchContactByName(response.getUserId(), secondContact.getName());

        assertNotNull(foundContact);
        assertEquals(secondContact.getName(), foundContact.getName());
        assertEquals(secondContact.getPhoneNumber(), foundContact.getPhoneNumber());

        try {
            String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(foundContact);
            System.out.println(jsonOutput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}