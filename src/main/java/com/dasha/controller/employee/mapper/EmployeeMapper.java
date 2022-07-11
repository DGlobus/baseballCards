package com.dasha.controller.employee.mapper;

import com.dasha.controller.employee.dto.ContactsDto;
import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.model.Contacts;
import com.dasha.model.Employee;
import com.dasha.model.Post;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParamsForService;
import com.dasha.util.ioutils.parse.EmployeeParsed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    List<EmployeeDto> toListDto(List<Employee> employees);

    @Mapping(target = "contacts.email", source = "parsed.email")
    @Mapping(target = "contacts.phone", source = "parsed.phone")
    @Mapping(target = "contacts.workEmail", source = "parsed.workEmail")
    CreateEmployeeParams toParams(EmployeeParsed parsed);

    CreateEmployeeParams toParams(CreateEmployeeDto dto);

    UpdateEmployeeParams toParams(UpdateEmployeeDto dto, UUID id);

    @Mapping(target = "id", source = "params.id")
    UpdateEmployeeParamsForService toParams(UpdateEmployeeParams params, Post post);

    Contacts toContact(ContactsDto dto);
}
