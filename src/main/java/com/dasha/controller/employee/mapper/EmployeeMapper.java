package com.dasha.controller.employee.mapper;

import com.dasha.controller.employee.dto.ContactsDto;
import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.model.Contacts;
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

    List<EmployeeDto> toListDto(List<Employee> employees);

    @Mapping(target = "contacts.email", source = "parsed.email")
    @Mapping(target = "contacts.phone", source = "parsed.phone")
    @Mapping(target = "contacts.workEmail", source = "parsed.workEmail")
    CreateEmployeeParams toParams(EmployeeParsed parsed);

    CreateEmployeeParams toParams(CreateEmployeeDto dto);

    @Mapping(target = "post.id", source = "dto.postId")
    UpdateEmployeeParams toParams(UpdateEmployeeDto dto);

    Contacts toContact(ContactsDto dto);
}
