package com.dasha.action;

import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.model.Contacts;
import com.dasha.model.Employee;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransformEmployeeDtoAction {
    private final PostService postService;
    private final EmployeeService employeeService;
    
    public Employee transform(CreateEmployeeDto createEmployeeDto){
        Post post = postService.getById(createEmployeeDto.getPostId());

        Contacts contacts = Contacts.builder()
                                    .email(createEmployeeDto.getEmail())
                                    .phone(createEmployeeDto.getPhone())
                                    .workEmail(createEmployeeDto.getWorkEmail())
                                    .build();

        CreateEmployeeParams params = CreateEmployeeParams.builder()
                                                            .firstName(createEmployeeDto.getFirstName())
                                                            .lastName(createEmployeeDto.getLastName())
                                                            .description(createEmployeeDto.getDescription())
                                                            .characteristics(createEmployeeDto.getCharacteristics())
                                                            .post(post)
                                                            .contacts(contacts)
                                                            .jobType(chooseJobType(createEmployeeDto.getJobType()))
                                                            .build();

        return employeeService.create(params);
    }

    public Employee transform(UpdateEmployeeDto newParams, Employee oldParams){
        UUID postId = newParams.getPostId() != null ? newParams.getPostId() : oldParams.getPost().getId();

        Contacts contacts = Contacts.builder()
                                    .email(checkString(oldParams.getContacts().getEmail(), newParams.getEmail()))
                                    .phone(checkString(oldParams.getContacts().getPhone(), newParams.getPhone()))
                                    .workEmail(checkString(oldParams.getContacts().getWorkEmail(), newParams.getWorkEmail()))
                                    .build();

        CreateEmployeeParams params = CreateEmployeeParams.builder()
                                                            .firstName(checkString(oldParams.getFirstName(), newParams.getFirstName()))
                                                            .lastName(checkString(oldParams.getLastName(), newParams.getLastName()))
                                                            .description(checkString(oldParams.getDescription(), newParams.getDescription()))
                                                            .characteristics(checkList(oldParams.getCharacteristics(), newParams.getCharacteristics()))
                                                            .post(postService.getById(postId))
                                                            .build();

        return employeeService.update(newParams.getId(), params);
    }


    private String checkString(String oldParam, String newParam){
        return newParam.equals("") ? oldParam : newParam;
    }


    private List<String> checkList(List<String> oldChar, List<String> newChar) {
        return newChar != null ? newChar : oldChar;
    }

        private JobType chooseJobType(String jobType) {
        return switch (jobType.toLowerCase(Locale.ROOT)) {
            case "permanent" -> JobType.PERMANENT;
            case "temporary" -> JobType.TEMPORARY;
            case "contract" -> JobType.CONTRACT;
            case "full_time" -> JobType.FULL_TIME;
            case "part_time" -> JobType.PART_TIME;
            default -> throw new IllegalArgumentException("Такого типа работника не существует");
        };
    }
}
