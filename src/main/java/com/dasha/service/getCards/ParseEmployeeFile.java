package com.dasha.service.getCards;

import com.dasha.model.Employee;

import java.util.List;

public interface ParseEmployeeFile {

    List<Employee> read(String path);
}
