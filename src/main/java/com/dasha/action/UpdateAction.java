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

    public Employee update(UpdateEmployeeParams params){

        params.setPost(postService.getById(params.getPost().getId()));

        return employeeService.update(params);
    }

}
