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
        Comparator<Employee> employeeComparator = Comparator.comparing(Employee::getLastName).thenComparing(Employee::getFirstName);

        employees = employees.stream()
                .sorted((emp1, emp2)->  employeeComparator.compare(emp1, emp2))
                .collect(Collectors.toList());
    }

}
