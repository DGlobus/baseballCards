package com.dasha.service.employee.params;

import com.dasha.model.Contacts;
import com.dasha.model.JobType;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class UpdateEmployeeParams {
    @NotNull
    UUID id;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    String description;
    @NotNull
    List<String> characteristics;
    UUID postId;
    @NotNull
    Contacts contacts;
    @NotNull
    JobType jobType;
}
