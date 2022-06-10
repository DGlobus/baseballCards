package com.dasha.exceptions;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime time;
    private String message;
    private String details;

}
