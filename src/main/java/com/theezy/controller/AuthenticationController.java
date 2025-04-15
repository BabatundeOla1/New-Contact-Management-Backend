//package com.theezy.controller;
//
//import com.theezy.dto.requests.AuthenticationRequest;
//import com.theezy.dto.requests.RegisterRequest;
//import com.theezy.dto.responses.AuthenticationResponse;
//import com.theezy.services.AuthenticationServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/auth/")
//@RequiredArgsConstructor
//public class AuthenticationController {
//
//    @Autowired
//    private final AuthenticationServiceImpl service;
//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
//        return ResponseEntity.ok(service.register(registerRequest));
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest){
//        return ResponseEntity.ok(service.login(authenticationRequest));
//    }
//}
