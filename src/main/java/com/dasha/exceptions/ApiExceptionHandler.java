package com.dasha.exceptions;

import com.dasha.exceptions.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public ErrorDetails handleItemNotFoundException(ItemNotFoundException exception, WebRequest request){
        return new ErrorDetails(exception.getMessage());
    }
}
