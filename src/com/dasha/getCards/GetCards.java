package com.dasha.getCards;

import com.dasha.Employee;

import java.util.List;

public interface GetCards {

    List<Employee> read(String path);
}
