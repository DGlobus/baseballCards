package com.dasha.controller.employee;

import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Employee;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.SearchEmployeeParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeDto add(@RequestBody @Valid CreateEmployeeDto dto){
        Employee employee = employeeService.create(mapper.toParams(dto));
        return mapper.toDto(employee);
    }

    @PostMapping("/update/{id}")
    public EmployeeDto update(@RequestBody @Valid UpdateEmployeeDto dto) throws ItemNotFoundException{
        Employee newEmp = employeeService.update(dto.getId(), mapper.toParams(dto));
        return mapper.toDto(newEmp);

    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable UUID id){
        employeeService.delete(id);
    }

    @PostMapping("/getById/{id}")
    public EmployeeDto getById(@PathVariable UUID id) throws ItemNotFoundException {
        return mapper.toDto(employeeService.getById(id));
    }

    @PostMapping("/getAll")
    public List<EmployeeDto> getAll(@RequestParam(required = false) String name, @RequestParam(required = false) UUID postId){
        return getAll(name, postId, false);
    }

    @PostMapping("/getAll/sorted")
    public List<EmployeeDto> getAllSorted(@RequestParam(required = false) String name, @RequestParam(required = false) UUID postId){
        return getAll(name, postId, true);
    }

    private List<EmployeeDto> getAll(@RequestParam(required = false) String name, @RequestParam(required = false) UUID postId, boolean isSorting){
        SearchEmployeeParams param = new SearchEmployeeParams(name, postId);
        return mapper.toDto(employeeService.getAll(param, isSorting));
    }
}
