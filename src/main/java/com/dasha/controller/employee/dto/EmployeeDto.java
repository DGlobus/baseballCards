package com.dasha.controller.employee.dto;

import com.dasha.model.Contacts;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class EmployeeDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private Post post;
    private Contacts contacts;
    private JobType jobType;
}
