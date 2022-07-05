package com.dasha.controller.employee.dto;

import com.dasha.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
//@Builder
@AllArgsConstructor
public class UpdateEmployeeDto {
    @NotNull
    private UUID id;
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private UUID postId;
    private String email;
    private String phone;
    private String workEmail;
    private String jobType;
}
