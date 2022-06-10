package com.dasha.controller.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String description;
    @NotNull
    private List<String> characteristics;
    @NotNull
    private UUID postId;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    private String workEmail;
    @NotNull
    private String jobType;
}

