package com.dasha.exceptions.exception;

public class ItemNotFoundException extends IllegalArgumentException {


    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}
