package com.theezy.services;

import com.theezy.data.models.Contact;
import com.theezy.data.repositories.ContactRepository;
import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.UserLoginRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.UserLoginResponse;
import com.theezy.utils.exception.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @BeforeEach
    public void deleteBeforeEachMethod(){
        userRepository.deleteAll();
    }
    @BeforeEach
    public void clearContactRepository(){
        contactRepository.deleteAll();
    }

    public void setUpUserLogin(UserLoginRequest userLoginRequest){
        userLoginRequest.setEmail("Useremail@gmail.com");
        userLoginRequest.setPassword("Babatunde123");
    }
    public void setUpNewUser(UserRegisterRequest userRegisterRequest){
        Contact contact = new Contact();
        contact.setEmail("Useremail@gmail.com");
        contact.setPhoneNumber("09036011444");
        contact.setName("You");
        contactRepository.save(contact);

        userRegisterRequest.setContact(contact);
        userRegisterRequest.setPassword("Babatunde123");
        userRegisterRequest.setFirstName("Babatunde");
        userRegisterRequest.setLastName("Olaleye");
    }
    @Test
    public void testThatUserCanRegister(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpNewUser(userRegisterRequest);
        userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());
    }
    @Test
    public  void testThatA_UserCantRegisterWithSameDetails(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        setUpNewUser(userRegisterRequest);
        userService.registerUser(userRegisterRequest);
        assertEquals(1, userRepository.count());

        UserRegisterRequest userWithSameDetails = new UserRegisterRequest();
        Contact contact = new Contact();
        contact.setEmail("Useremail@gmail.com");
        contact.setPhoneNumber("09036011444");
        contact.setName("You");
        contactRepository.save(contact);

        userWithSameDetails.setContact(contact);
        userWithSameDetails.setPassword("Babatunde123");
        userWithSameDetails.setFirstName("Babatunde");
        userWithSameDetails.setLastName("Olaleye");
        assertThrows(UserAlreadyExistException.class, ()-> userService.registerUser(userWithSameDetails));
    }

    @Test
    public void testThatUserCanLogin(){
        Object o = contactRepository.findByName("rof");
        assertNotNull(o);

    }
}