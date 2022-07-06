package com.dasha.controller.employee.dto;

import com.dasha.model.Contacts;
import com.dasha.model.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdateEmployeeDto {
    @NotNull
    private String firstName;
    @NotNull
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
