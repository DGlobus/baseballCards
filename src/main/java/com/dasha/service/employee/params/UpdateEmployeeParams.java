package com.dasha.service.employee.params;

import com.dasha.model.Contacts;
import com.dasha.model.Post;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateEmployeeParams {
    @NonNull
    private UUID id;
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private UUID postId;
    private Contacts contacts;
    private String jobType;
}
