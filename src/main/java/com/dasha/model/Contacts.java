package com.dasha.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Contacts {
    @NotNull
    private String phone;
    @NotNull
    private String email;
    private String workEmail;
}
