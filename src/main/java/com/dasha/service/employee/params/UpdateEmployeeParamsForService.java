package com.dasha.service.employee.params;

import com.dasha.model.Contacts;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class UpdateEmployeeParamsForService {
    @NotNull
    UUID id;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    String description;
    @NotNull
    List<String> characteristics;
    @NotNull
    Post post;
    @NotNull
    Contacts contacts;
    @NotNull
    JobType jobType;
}
