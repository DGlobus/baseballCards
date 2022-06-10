package com.dasha.controller.employee.mapper;

import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.model.Employee;
import com.dasha.util.ioutils.parse.EmployeeParsed;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(Employee employee);

    CreateEmployeeDto toDto(EmployeeParsed parsed);
}
