package com.dasha.action;

import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.model.Employee;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParamsForService;
import com.dasha.service.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateEmployeeAction {

    private final PostService postService;
    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;

    public Employee update(UpdateEmployeeParams params){

        UpdateEmployeeParamsForService parametrs = mapper.toParams(params, postService.getById(params.getPostId()));

        return employeeService.update(parametrs);
    }

}
