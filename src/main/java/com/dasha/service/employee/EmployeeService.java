package com.dasha.service.employee;

import com.dasha.action.UpdateAction;
import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Employee;
import com.dasha.model.JobType;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.employee.params.SearchEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService{

    private final Map<UUID, Employee> employees = new HashMap<>();
    private final EmployeeListService employeeListService = new EmployeeListService();
    private final UpdateAction action = new UpdateAction();

    public Employee create(CreateEmployeeParams createEmployeeParams){
        Employee employee = createEmployee(createEmployeeParams);
        employees.put(employee.getId(), employee);
        return employee;
    }

    public Employee update(@NonNull UUID employeeId, UpdateEmployeeParams params){
        if(!employees.containsKey(employeeId)){
            throw new ItemNotFoundException("Данного работника не существует " + employeeId);
        }
        Employee updateEmployee = getById(employeeId);
        action.update(updateEmployee, params);
        return updateEmployee;
    }

    public void delete(@NonNull UUID employeeId){
        employees.remove(employeeId);
    }

    public Employee getById(@NonNull UUID employeeId){
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

    private List<Employee> sort(List<Employee> list){
        return employeeListService.sort(list);
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
                        .jobType(chooseJobType(createEmployeeParams.getJobType()))
                        .build();
    }

    private JobType chooseJobType(String jobType) {
        try {
            return JobType.valueOf(jobType);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}

