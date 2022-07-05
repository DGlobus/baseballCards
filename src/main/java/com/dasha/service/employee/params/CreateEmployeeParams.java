package com.dasha.service.employee.params;

import com.dasha.model.Contacts;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreateEmployeeParams {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String description;
    @NonNull
    private List<String> characteristics;
    @NonNull
    private Post post;
    @NonNull
    private Contacts contacts;
    @NonNull
    private String jobType;
}
