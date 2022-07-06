package com.dasha.action;

import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.model.Employee;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import com.dasha.service.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateAction {

    private final PostService postService;
    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;

    public Employee update(UUID id, UpdateEmployeeDto dto){

        UpdateEmployeeParams params = mapper.toParams(dto);
        if(dto.getPostId() != null) params.setPost(postService.getById(dto.getPostId()));

        return employeeService.update(id, params);
    }

}
