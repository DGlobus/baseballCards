package com.dasha.service.employee;

import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Employee;
import com.dasha.model.Post;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.employee.params.SearchEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService{

    private final Map<UUID, Employee> employees = new HashMap<>();
    private final EmployeeListService employeeListService = new EmployeeListService();

    public Employee create(CreateEmployeeParams createEmployeeParams, Post post){
        Employee employee = createEmployee(createEmployeeParams, post);
        employees.put(employee.getId(), employee);
        return employee;
    }

    public Employee getById(@NotNull UUID employeeId){
        if(!employees.containsKey(employeeId)){
            throw new ItemNotFoundException("Данного работника не существует " + employeeId);
        }
        return employees.get(employeeId);
    }

    public List<Employee> getAll(SearchEmployeeParams params, boolean isSorting){
        List<Employee> list = new ArrayList<>(employees.values());
        list = employeeListService.filter(list, params);
        return isSorting ? sort(list) : list;
    }

    public Employee update(UpdateEmployeeParams params){
        Employee employee = getById(params.getId());
        updateEmployee(employee, params);
        return employee;
    }

    public void delete(@NotNull UUID employeeId){
        employees.remove(employeeId);
    }

    private List<Employee> sort(List<Employee> list){
        return employeeListService.sort(list);
    }

    private Employee createEmployee(CreateEmployeeParams createEmployeeParams, Post post) {

        return Employee.builder()
                        .id(UUID.randomUUID())
                        .firstName(createEmployeeParams.getFirstName())
                        .lastName(createEmployeeParams.getLastName())
                        .description(createEmployeeParams.getDescription())
                        .characteristics(createEmployeeParams.getCharacteristics())
                        .post(post)
                        .contacts(createEmployeeParams.getContacts())
                        .jobType(createEmployeeParams.getJobType())
                        .build();
    }

    private void updateEmployee(Employee employee, UpdateEmployeeParams params) {

        employee.setFirstName(params.getFirstName());
        employee.setLastName(params.getLastName());
        employee.setDescription(params.getDescription());
        employee.setCharacteristics(params.getCharacteristics());
        employee.setContacts(params.getContacts());
        employee.setPost(params.getPost());
        employee.setJobType(params.getJobType());
    }

}

