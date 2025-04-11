package com.theezy.utils.exception;

public class UserLoginDetailsInvalid extends RuntimeException{
    public UserLoginDetailsInvalid(String message){
        super(message);
    }
}
