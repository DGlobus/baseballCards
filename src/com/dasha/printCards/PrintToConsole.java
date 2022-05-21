package com.dasha.printCards;

import com.dasha.Employee;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PrintToConsole implements PrintCards{
    List<Employee> employees;

    public PrintToConsole(List<Employee> employees){
        this.employees = employees;
    }

    @Override
    public void print() {
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        employees.stream().forEach((a) -> ps.print(a.toString()));
    }

    @Override
    public void sort() {
        Comparator<Employee> comparator = new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                if(emp1.getLastName().equals(emp2.getLastName())){
                    return emp1.getFirstName().compareTo(emp2.getFirstName());
                }
                else{
                    return emp1.getLastName().compareTo(emp2.getLastName());
                }
            }
        };

        employees = employees.stream()
                .sorted((emp1, emp2)->  comparator.compare(emp1, emp2))
                .collect(Collectors.toList());
    }

}
