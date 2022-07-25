package com.dasha.service.employee;

import com.dasha.model.Employee;
import com.dasha.model.Post;
import com.dasha.repository.EmployeeRepository;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.employee.params.SearchEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParamsForService;
import com.dasha.util.Guard;
import com.dasha.util.logging.UpdateLogging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeListService employeeListService = new EmployeeListService();

    @Transactional
    public Employee create(CreateEmployeeParams createEmployeeParams, Post post) {
        Employee employee = createEmployee(createEmployeeParams, post);
        repository.save(employee);
        return employee;
    }

    @Transactional
    public Employee getById(@NotNull UUID employeeId) {
        Guard.checkAndThrowItemNotFoundException(repository.existsById(employeeId), "Данного работника не существует " + employeeId);
        return repository.findById(employeeId).orElse(null);
    }

    @Transactional
    public List<Employee> getAll(SearchEmployeeParams params, boolean isSorting) {
        List<Employee> list = new ArrayList<>(repository.findAll());
        list = employeeListService.filter(list, params);
        return isSorting ? sort(list) : list;
    }

    @Transactional
    @UpdateLogging
    public Employee update(UpdateEmployeeParamsForService params) {
        Employee employee = getById(params.getId());
        updateEmployee(employee, params);
        return employee;
    }

    @Transactional
    public void delete(@NotNull UUID employeeId) {
        repository.deleteById(employeeId);
    }

    private List<Employee> sort(List<Employee> list) {
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

    private void updateEmployee(Employee employee, UpdateEmployeeParamsForService params) {
        employee.setFirstName(params.getFirstName());

        employee.setLastName(params.getLastName());
        employee.setDescription(params.getDescription());
        employee.setCharacteristics(params.getCharacteristics());
        employee.setContacts(params.getContacts());
        employee.setPost(params.getPost());
        employee.setJobType(params.getJobType());
    }

    private void logging(UUID id, String previousValue, String newValue) {
        if (!previousValue.equals(newValue))
            log.info(String.format("id: {id}\t befor: {previous}\t after:{newOne}", id, previousValue, newValue));
    }

}

