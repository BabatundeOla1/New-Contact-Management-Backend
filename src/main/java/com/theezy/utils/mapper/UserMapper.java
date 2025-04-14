package com.theezy.utils.mapper;

import com.theezy.data.models.User;
import com.theezy.dto.requests.UserLoginRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.UserLoginResponse;
import com.theezy.dto.responses.UserRegisterResponse;

public class UserMapper {

    public static User mapRequestToUser(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setId(userRegisterRequest.getId());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setContact(userRegisterRequest.getContact());
        user.setContacts(userRegisterRequest.getContacts());
        user.setPassword(userRegisterRequest.getPassword());
        return user;
    }

    public static UserRegisterResponse mapUserToResponse(User user){
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setMessage("Registered successfully");
        userRegisterResponse.setData(user.getId());
        userRegisterResponse.setUserId(user.getId());
        return userRegisterResponse;
    }

    public static UserLoginResponse mapLoginToResponse(String message){
        UserLoginResponse loginResponse = new UserLoginResponse();
        loginResponse.setMessage(message);
        loginResponse.setStatus(true);
        return loginResponse;
    }

}
