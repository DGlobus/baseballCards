package com.dasha.action;

import com.dasha.model.Employee;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmployeeAction {

    private final PostService postService;
    private final EmployeeService employeeService;

    public Employee create(CreateEmployeeParams params) {
        return employeeService.create(params, postService.getById(params.getPostId()));
    }
}
