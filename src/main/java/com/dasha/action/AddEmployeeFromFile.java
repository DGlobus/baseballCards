package com.dasha.action;

import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.model.Employee;
import com.dasha.service.employee.EmployeeService;
import com.dasha.util.ioutils.parse.EmployeeParsed;
import com.dasha.util.ioutils.parse.ParseEmployeeFile;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AddEmployeeFromFile {
    private final ParseEmployeeFile parse;
    private final EmployeeMapper mapper;
    private final EmployeeService employeeService;

    public List<Employee> execute(String path){
        List<EmployeeParsed> parsed = parse.read(path);
        List<Employee> employees = new ArrayList<>();

        for(EmployeeParsed emp : parsed){
            CreateEmployeeDto dto = mapper.toDto(emp);
            employees.add(employeeService.create(mapper.toParams(dto)));
        }

        return employees;
    }
}
