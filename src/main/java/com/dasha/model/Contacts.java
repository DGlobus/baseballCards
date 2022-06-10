package com.dasha.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contacts {
    private String phone;
    private String email;
    private String workEmail;
}
