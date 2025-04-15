package com.theezy.utils.exception;

public class OtpExpiredException extends RuntimeException{

    public OtpExpiredException(String message){
        super(message);
    }
}
