package com.theezy.controller;

import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.UserLoginRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.UserLoginResponse;
import com.theezy.dto.responses.UserRegisterResponse;
import com.theezy.services.UserService;
import com.theezy.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mycallerApp/")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest){
        return new ResponseEntity<>(userService.registerUser(userRegisterRequest), HttpStatus.OK);
    }
    @PostMapping("/userLogin")
    public ResponseEntity<UserLoginResponse> registerUser(@RequestBody UserLoginRequest userLoginRequest){
        return new ResponseEntity<>(userService.login(userLoginRequest), HttpStatus.OK);
    }
}
