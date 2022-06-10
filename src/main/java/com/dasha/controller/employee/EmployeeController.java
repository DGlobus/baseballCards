package com.dasha.controller.employee;

import com.dasha.action.TransformEmployeeDtoAction;
import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.model.Employee;
import com.dasha.service.employee.params.SearchEmployeeParams;
import com.dasha.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;
    private final TransformEmployeeDtoAction createEmployeeArgumentAction;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeDto add(@RequestBody @Valid CreateEmployeeDto dto){
        Employee newEmp = createEmployeeArgumentAction.transform(dto);
        return mapper.toDto(newEmp);
    }

    @PostMapping("/update/{id}")
    public EmployeeDto update(@RequestBody @Valid UpdateEmployeeDto dto){
        Employee newEmp = createEmployeeArgumentAction.transform(dto, employeeService.getById(dto.getId()));
        return mapper.toDto(newEmp);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable UUID id){
        employeeService.delete(id);
    }

    @PostMapping("/getById/{id}")
    public EmployeeDto getById(@PathVariable UUID id){
        return mapper.toDto(employeeService.getById(id));
    }

    @PostMapping("/getAll")
    public List<EmployeeDto> getAll(@RequestParam(required = false) String name, @RequestParam(required = false) UUID postId){
        SearchEmployeeParams param = new SearchEmployeeParams(name, postId);
        return employeeService.getAll(param).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
