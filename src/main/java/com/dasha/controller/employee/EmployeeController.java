package com.dasha.controller.employee;

import com.dasha.action.AddEmployeeFromFile;
import com.dasha.action.CreateEmployeeAction;
import com.dasha.action.UpdateAction;
import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.model.Employee;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.SearchEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Контроллер пользователя")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;
    private final AddEmployeeFromFile addEmployeeFromFile;
    private final UpdateAction updateAction;
    private final CreateEmployeeAction createAction;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation("Добавить нового пользователя")
    public EmployeeDto add(@RequestBody @Valid CreateEmployeeDto dto){
        Employee employee = createAction.create(mapper.toParams(dto));
        return mapper.toDto(employee);
    }

    @PostMapping("/createFromLocalFile")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation("Добавить пользователей из файла")
    public List<EmployeeDto> createFromLocalFile(@RequestBody File file){
        return addEmployeeFromFile.execute(file);
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить пользователя")
    public EmployeeDto getById(@PathVariable UUID id) {
        return mapper.toDto(employeeService.getById(id));
    }

    @GetMapping("/getAll")
    @ApiOperation("Получить всех пользователей")
    private List<EmployeeDto> getAll(@RequestParam(required = false) String name, @RequestParam(required = false) UUID postId,
                                     @RequestParam(required = false, defaultValue = "true") boolean isSorting){
        SearchEmployeeParams param = new SearchEmployeeParams(name, postId);
        return mapper.toListDto(employeeService.getAll(param, isSorting));
    }

    @PostMapping("/{id}/update")
    @ApiOperation("Обновить данные пользователя")
    public EmployeeDto update(@PathVariable UUID id, @RequestBody @Valid UpdateEmployeeDto dto){
        UpdateEmployeeParams params = mapper.toParams(dto);
        params.setId(id);
        return mapper.toDto(updateAction.update(params));
    }

    @PostMapping("/{id}/delete")
    @ApiOperation("Удалить пользователя")
    public void delete(@PathVariable UUID id){
        employeeService.delete(id);
    }
}
