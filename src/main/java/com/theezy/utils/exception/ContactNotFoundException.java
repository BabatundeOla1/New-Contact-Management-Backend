package com.theezy.utils.exception;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException(String message){
        super(message);
    }
}
