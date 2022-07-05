package com.dasha.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
public class ErrorDetails {
    private String message;
}
