package com.dasha.service.employee.params;

import com.dasha.model.Contacts;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UpdateEmployeeParams {
    @NotNull
    private UUID id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String description;
    @NotNull
    private List<String> characteristics;
    private Post post;
    @NotNull
    private Contacts contacts;
    @NotNull
    private JobType jobType;
}
