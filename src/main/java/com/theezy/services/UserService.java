package com.theezy.services;

import com.theezy.dto.requests.UserLoginRequest;
import com.theezy.dto.requests.UserRegisterRequest;
import com.theezy.dto.responses.UserLoginResponse;
import com.theezy.dto.responses.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);

    UserLoginResponse login(UserLoginRequest userLoginRequest);
}
