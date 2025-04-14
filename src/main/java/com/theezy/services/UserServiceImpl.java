package com.theezy.services;

import com.theezy.data.models.User;
import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.UserLoginRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.UserLoginResponse;
import com.theezy.dto.responses.UserRegisterResponse;
import com.theezy.utils.exception.UserAlreadyExistException;
import com.theezy.utils.exception.UserCanNotBeNullException;
import com.theezy.utils.exception.UserLoginDetailsInvalid;
import com.theezy.utils.exception.UserNotFoundException;
import com.theezy.utils.mapper.UserMapper;
import com.theezy.utils.passwordHashed.PasswordHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        boolean isPhoneNumberExist = checkIfUserExist(userRegisterRequest.getContact().getPhoneNumber());

        boolean  isEmailExist = checkIfUserExistByEmail(userRegisterRequest.getContact().getEmail());

        if (isEmailExist || isPhoneNumberExist){
            throw new UserAlreadyExistException("User already exist");
        }

        String hashedPassword = PasswordHashingService.hashPassword(userRegisterRequest.getPassword());

        User newUser = new User();
        newUser.setFirstName(userRegisterRequest.getFirstName());
        newUser.setLastName(userRegisterRequest.getLastName());
        newUser.setPassword(hashedPassword);
        newUser.setContact(userRegisterRequest.getContact());
        newUser.getContact().setName("YOU");
        newUser.setContacts(userRegisterRequest.getContacts());
        userRepository.save(newUser);

        return UserMapper.mapUserToResponse(newUser);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User foundUser = userRepository.findUserByContact_Email(userLoginRequest.getEmail())
                .orElseThrow(() -> new UserCanNotBeNullException("User not found"));

        if (foundUser == null){
            throw new UserCanNotBeNullException("User Not found");
        }

        boolean isPasswordValid = PasswordHashingService.checkPassword(
                userLoginRequest.getPassword(),
                foundUser.getPassword());

        if (!isPasswordValid){
            throw new UserLoginDetailsInvalid("Invalid details");
        }

        return UserMapper.mapLoginToResponse("Login successful.");

    }

    private boolean checkIfUserExist(String phoneNumber){
        return userRepository.existsUserByContact_PhoneNumber(phoneNumber);
    }
    private boolean checkIfUserExistByEmail(String email){
        return userRepository.existsUserByContact_Email(email);
    }
}
