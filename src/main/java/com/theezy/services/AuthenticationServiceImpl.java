//package com.theezy.services;
//
//import com.theezy.data.models.JWTService;
//import com.theezy.data.models.Role;
//import com.theezy.data.repositories.UserRepository;
//import com.theezy.dto.requests.AuthenticationRequest;
//import com.theezy.dto.requests.RegisterRequest;
//import com.theezy.dto.responses.AuthenticationResponse;
//import lombok.RequiredArgsConstructor;
////import lombok.var;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationServiceImpl {
//
//    @Autowired
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JWTService jwtService;
//
//    public AuthenticationResponse register(RegisterRequest request){
//        var user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//
//    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
//        return null;
//    }
//}
