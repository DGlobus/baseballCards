package com.dasha.exceptions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ItemNotFoundException extends IllegalArgumentException{


    public ItemNotFoundException(){
        super();
    }

    public ItemNotFoundException(String message){
        super(message);
    }
}
