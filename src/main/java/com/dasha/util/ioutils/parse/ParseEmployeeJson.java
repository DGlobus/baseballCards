package com.dasha.util.ioutils.parse;

import com.dasha.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ParseEmployeeJson implements ParseEmployeeFile {

    private List<EmployeeParsed> employee;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<EmployeeParsed> read(String path) {

        try {
            employee = mapper.readValue(new File(path), new TypeReference<List<EmployeeParsed>>() {
            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return employee;
    }


    private String putParam(Object obj){
        return obj != null ? obj.toString() : null;
    }
}
