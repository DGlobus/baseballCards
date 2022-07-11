package com.dasha.service.employee.params;

import com.dasha.model.Contacts;
import com.dasha.model.JobType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CreateEmployeeParams {
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
    private Contacts contacts;
    @NotNull
    private JobType jobType;
}
