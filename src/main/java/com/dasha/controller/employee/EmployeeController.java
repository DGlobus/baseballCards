package com.dasha.controller.employee;

import com.dasha.action.AddEmployeeFromFile;
import com.dasha.action.CreateAction;
import com.dasha.action.UpdateAction;
import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.model.Employee;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.SearchEmployeeParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;
    private final AddEmployeeFromFile addEmployeeFromFile;
    private final UpdateAction updateAction;
    private final CreateAction createAction;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeDto add(@RequestBody @Valid CreateEmployeeDto dto){
        Employee employee = createAction.create(mapper.toParams(dto));
        return mapper.toDto(employee);
    }

    @PostMapping("/create/fromFile")
    @ResponseStatus(code = HttpStatus.CREATED)
    public List<EmployeeDto> createFromFile(@RequestBody File file){
        return addEmployeeFromFile.execute(file);
    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable UUID id) {
        return mapper.toDto(employeeService.getById(id));
    }

    @GetMapping("/getAll")
    private List<EmployeeDto> getAll(@RequestParam(required = false) String name, @RequestParam(required = false) UUID postId,
                                     @RequestParam boolean isSorting){
        SearchEmployeeParams param = new SearchEmployeeParams(name, postId);
        return mapper.toListDto(employeeService.getAll(param, isSorting));
    }

    @PostMapping("/{id}/update")
    public EmployeeDto update(@PathVariable UUID id, @RequestBody @Valid UpdateEmployeeDto dto){
        return mapper.toDto(updateAction.update(id, dto));
    }

    @PostMapping("/{id}/delete")
    public void delete(@PathVariable UUID id){
        employeeService.delete(id);
    }
}
