package com.api.tcc.handler;

public class CarAlreadyExistsException extends RuntimeException{

    public CarAlreadyExistsException(String msg){
        super(msg);
    }
}
