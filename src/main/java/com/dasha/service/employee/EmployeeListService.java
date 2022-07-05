package com.dasha.service.employee;

import com.dasha.model.Employee;
import com.dasha.service.employee.params.SearchEmployeeParams;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeListService {

    public List<Employee> filter(List<Employee> employees, SearchEmployeeParams search) {

        if(search == null)
            return employees;

        Predicate<Employee> isFiltered = emp -> employeeContainsName(search.getName(), emp.getFirstName());
        isFiltered = isFiltered.or(emp -> employeeContainsName(search.getName(), emp.getLastName()));
        isFiltered = isFiltered.and(emp -> employeeEqualsID(search.getId(), emp.getPost().getId()));

        return employees.stream().filter(isFiltered).collect(Collectors.toList());
    }

    private boolean employeeContainsName(String searchParametr, String employeeParam){
        if(searchParametr!=null){
            return employeeParam.toLowerCase(Locale.ROOT).contains(searchParametr);
        }
        return true;
    }

    private boolean employeeEqualsID(UUID searchParametr, UUID employeeParam){
        if(searchParametr!=null){
            return employeeParam.equals(searchParametr);
        }
        return true;
    }

    public List<Employee> sort(List<Employee> employees) {
        Comparator<Employee> employeeComparator = Comparator.comparing(Employee::getLastName).thenComparing(Employee::getFirstName);
        List<Employee> changed = new ArrayList<>(employees);

        return changed.stream()
                .sorted(employeeComparator)
                .collect(Collectors.toList());
    }
}
