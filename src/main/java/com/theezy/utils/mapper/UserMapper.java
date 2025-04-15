package com.theezy.utils.mapper;

import com.theezy.data.models.User;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.UserLoginResponse;
import com.theezy.dto.responses.UserRegisterResponse;

public class UserMapper {

    public static User mapRequestToUser(String hashedPassword, UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setId(userRegisterRequest.getId());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setContact(userRegisterRequest.getContact());
        user.setContacts(userRegisterRequest.getContacts());
        user.setVerified(userRegisterRequest.isVerified());
        user.setPassword(hashedPassword);
        return user;
    }

    public static UserRegisterResponse mapUserToResponse(String jwtToken, User user){
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setMessage("Registered successfully, Check email for verification code");
        userRegisterResponse.setUserId(user.getId());
        userRegisterResponse.setToken(jwtToken);
        return userRegisterResponse;
    }

    public static UserLoginResponse mapLoginToResponse(String jwtToken, String message){
        UserLoginResponse loginResponse = new UserLoginResponse();
        loginResponse.setMessage(message);
        loginResponse.setStatus(true);
        loginResponse.setToken(jwtToken);
        return loginResponse;
    }
}
