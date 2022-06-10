package com.dasha.service.employee;

import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Employee;
import com.dasha.service.ModelService;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.employee.params.SearchEmployeeParams;
import com.dasha.service.post.PostService;
import com.dasha.util.Guard;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService implements ModelService<Employee, CreateEmployeeParams> {

    private final Map<UUID, Employee> employees;
    private final EmployeeListService employeeListService;

    @Override
    public Employee create(CreateEmployeeParams createEmployeeParams){
        Employee employee = createEmployee(createEmployeeParams);
        employees.put(employee.getId(), employee);
        return employee;
    }

    @Override
    public Employee update(@NonNull UUID employeeId, CreateEmployeeParams params){
        if(!employees.containsKey(employeeId)){
            throw new ItemNotFoundException("Данного работника не существует");
        }
        Employee updateEmployee = createEmployee(params);
        updateEmployee.setId(employeeId);
        employees.replace(employeeId, updateEmployee);
        return updateEmployee;
    }

    @Override
    public void delete(@NonNull UUID employeeId){
        employees.remove(employeeId);
    }

    @Override
    public Employee getById(@NonNull UUID employeeId){
        if(!employees.containsKey(employeeId)){
            throw new ItemNotFoundException("Данного работника не существует");
        }
        return employees.get(employeeId);
    }

    @Override
    public List<Employee> getAll(){
        return new ArrayList<>(employees.values());
    }

    public List<Employee> getAll(SearchEmployeeParams params){
        List<Employee> list = new ArrayList<>(employees.values());
        list = employeeListService.filter(list, params);
        list = employeeListService.sort(list);
        return list;
    }

    private Employee createEmployee(CreateEmployeeParams createEmployeeParams) {

        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName(createEmployeeParams.getFirstName())
                .lastName(createEmployeeParams.getLastName())
                .description(createEmployeeParams.getDescription())
                .characteristics(createEmployeeParams.getCharacteristics())
                .post(createEmployeeParams.getPost())
                .contacts(createEmployeeParams.getContacts())
                .jobType(createEmployeeParams.getJobType())
                .build();
    }
}

