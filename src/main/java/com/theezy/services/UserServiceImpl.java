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
import com.theezy.utils.exception.UserAlreadyExistException;
import com.theezy.utils.exception.UserCanNotBeNullException;
import com.theezy.utils.exception.UserLoginDetailsInvalid;
import com.theezy.utils.exception.UserNotFoundException;
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

        otpVerificationService.sendOtp( OtpVerificationMapper.mapToOtpSendRequest(newUser.getContact().getEmail()));

        String jwtToken = jwtService.generateToken(newUser);
        UserRegisterResponse response = UserMapper.mapUserToResponse(jwtToken, newUser);

        return response;
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

        String jwtToken = jwtService.generateToken(foundUser);

        UserLoginResponse response = UserMapper.mapLoginToResponse(jwtToken, "Login successful.");
        return response;
    }

    private boolean checkIfUserExist(String phoneNumber){
        return userRepository.existsUserByContact_PhoneNumber(phoneNumber);
    }
    private boolean checkIfUserExistByEmail(String email){
        return userRepository.existsUserByContact_Email(email);
    }
}
