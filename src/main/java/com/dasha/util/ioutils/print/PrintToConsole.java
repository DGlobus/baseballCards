package com.dasha.util.ioutils.print;

import com.dasha.model.Employee;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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


}
