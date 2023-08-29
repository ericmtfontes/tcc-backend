package com.api.tcc.handler;

public class NotRentedException extends RuntimeException{

    public NotRentedException(String msg){
        super(msg);
    }
}
