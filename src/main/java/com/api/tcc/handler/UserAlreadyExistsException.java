package com.api.tcc.handler;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}
