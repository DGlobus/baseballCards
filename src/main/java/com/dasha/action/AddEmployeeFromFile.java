package com.dasha.action;

import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.model.Employee;
import com.dasha.util.ioutils.parse.EmployeeParsed;
import com.dasha.util.ioutils.parse.ParseEmployeeFile;
import com.dasha.util.ioutils.parse.ParseEmployeeJson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AddEmployeeFromFile {
    private final ParseEmployeeFile parse = new ParseEmployeeJson();
    private final EmployeeMapper mapper;
    private final CreateAction createAction;

    public List<EmployeeDto> execute(File file){
        List<EmployeeParsed> parsed = parse.read(file);
        List<Employee> employees = new ArrayList<>();

        for(EmployeeParsed emp : parsed){
            CreateEmployeeDto dto = mapper.toDto(emp);
            employees.add(createAction.create(mapper.toParams(dto)));
        }

        return mapper.toListDto(employees);
    }
}
