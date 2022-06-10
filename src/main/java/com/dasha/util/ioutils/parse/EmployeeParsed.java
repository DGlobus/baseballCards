package com.dasha.util.ioutils.parse;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EmployeeParsed {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private UUID postId;
    private String email;
    private String phone;
    private String workEmail;
}
