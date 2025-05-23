package com.theezy.services;

import com.theezy.data.models.JWTService;
import com.theezy.data.models.Role;
import com.theezy.data.models.User;
import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.OtpSendRequest;
import com.theezy.dto.requests.UserLoginRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.UserLoginResponse;
import com.theezy.dto.responses.UserRegisterResponse;
import com.theezy.utils.exception.*;
import com.theezy.utils.mapper.OtpVerificationMapper;
import com.theezy.utils.mapper.UserMapper;
import com.theezy.utils.passwordHashed.PasswordHashingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private OtpVerificationService otpVerificationService;

    @Autowired
    private final JWTService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        boolean isPhoneNumberExist = checkIfUserExist(userRegisterRequest.getContact().getPhoneNumber());

        boolean  isEmailExist = checkIfUserExistByEmail(userRegisterRequest.getContact().getEmail());

        if (isEmailExist || isPhoneNumberExist){
            throw new UserAlreadyExistException("User already exist");
        }

        String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        User newUser = UserMapper.mapRequestToUser(hashedPassword, userRegisterRequest);
        newUser.getContact().setName("YOU");
        if (newUser.getRole() == null) {
            newUser.setRole(Role.USER);
        }
        userRepository.save(newUser);
        OtpSendRequest userEmail = OtpVerificationMapper.mapToOtpSendRequest(newUser.getContact().getEmail());

        try {

            otpVerificationService.sendOtp(userEmail);

        } catch (Exception e){
            userRepository.delete(newUser);
            throw new FailToSendOtpException("Network Error, Failed to send OTP: " + e.getMessage());
        }

        return UserMapper.mapUserToResponse(null, null, newUser, "Registration Successful");
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(),
                        userLoginRequest.getPassword()
                    )
            );
        }
        catch (AuthenticationException error){
            throw new UserLoginDetailsInvalid("Invalid email or password");
        }

        User foundUser = userRepository.findUserByContact_Email(userLoginRequest.getEmail())
                .orElseThrow(() -> new UserCanNotBeNullException("User not found"));

        String jwtAccessToken = jwtService.generateAccessToken(foundUser);
        String refreshToken = jwtService.generateRefreshToken(foundUser);
        return UserMapper.mapLoginToResponse(jwtAccessToken, refreshToken, "Login successful.");
    }

    private boolean checkIfUserExist(String phoneNumber){
        return userRepository.existsUserByContact_PhoneNumber(phoneNumber);
    }
    private boolean checkIfUserExistByEmail(String email){
        return userRepository.existsUserByContact_Email(email);
    }
}
