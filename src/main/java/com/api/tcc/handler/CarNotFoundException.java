package com.api.tcc.handler;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(String msg){
        super(msg);
    }
}
