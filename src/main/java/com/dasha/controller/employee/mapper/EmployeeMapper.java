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
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    CreateEmployeeDto toDto(EmployeeParsed parsed);

    @Mappings({
            @Mapping(target = "contacts.phone", source = "dto.phone"),
            @Mapping(target = "contacts.email", source = "dto.email"),
            @Mapping(target = "contacts.workEmail", source = "dto.workEmail")
    })
    EmployeeDto toDto(CreateEmployeeDto dto);

    List<EmployeeDto> toDto(List<Employee> employees);

    @Mappings({
            @Mapping(target = "contacts.phone", source = "dto.phone"),
            @Mapping(target = "contacts.email", source = "dto.email"),
            @Mapping(target = "contacts.workEmail", source = "dto.workEmail")
    })
    CreateEmployeeParams toParams(CreateEmployeeDto dto);

    @Mappings({
            @Mapping(target = "contacts.phone", source = "dto.phone"),
            @Mapping(target = "contacts.email", source = "dto.email"),
            @Mapping(target = "contacts.workEmail", source = "dto.workEmail")
    })
    UpdateEmployeeParams toParams(UpdateEmployeeDto dto);
}
