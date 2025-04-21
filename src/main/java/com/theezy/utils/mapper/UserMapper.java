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

    public static UserRegisterResponse mapUserToResponse(String jwtAccessToken, String refreshToken, User user, String message){
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setMessage(message);
        userRegisterResponse.setUserId(user.getId());
        userRegisterResponse.setRefreshToken(refreshToken);
        userRegisterResponse.setJwtAccessToken(jwtAccessToken);
        return userRegisterResponse;
    }

    public static UserLoginResponse mapLoginToResponse(String jwtToken, String refreshToken, String message){
        UserLoginResponse loginResponse = new UserLoginResponse();
        loginResponse.setMessage(message);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessToken(jwtToken);
        return loginResponse;
    }
}
