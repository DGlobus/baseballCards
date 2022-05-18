package com.dasha.printCards;

import com.dasha.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class PrintToConsole implements PrintCards{
    List<Employee> employees;

    public PrintToConsole(List<Employee> employees){
        this.employees = employees;
    }

    @Override
    public void print() {
        employees.stream().forEach((a) -> a.print());
    }

    @Override
    public void sort() {
        employees = employees.stream()
                .sorted((emp1, emp2)->  emp1.getLastName().equals(emp2.getLastName())?
                        emp1.getFirstName().compareTo(emp2.getFirstName()):
                        emp1.getLastName().compareTo(emp2.getLastName()))
                .collect(Collectors.toList());
    }

}
