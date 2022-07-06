package com.dasha.controller.employee.mapper;

import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.model.Employee;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import com.dasha.util.ioutils.parse.EmployeeParsed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    @Mapping(target = "contacts.email", source = "parsed.email")
    @Mapping(target = "contacts.phone", source = "parsed.phone")
    @Mapping(target = "contacts.workEmail", source = "parsed.workEmail")
    CreateEmployeeDto toDto(EmployeeParsed parsed);

    EmployeeDto toDto(CreateEmployeeDto dto);

    List<EmployeeDto> toListDto(List<Employee> employees);

    CreateEmployeeParams toParams(CreateEmployeeDto dto);

    UpdateEmployeeParams toParams(UpdateEmployeeDto dto);
}
