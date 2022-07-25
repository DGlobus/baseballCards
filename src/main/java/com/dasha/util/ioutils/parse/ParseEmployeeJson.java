package com.dasha.util.ioutils.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ParseEmployeeJson implements ParseEmployeeFile {

    private final ObjectMapper mapper = new ObjectMapper();
    private List<EmployeeParsed> employee;

    @Override
    public List<EmployeeParsed> read(File file) {

        try {
            employee = mapper.readValue(file, new TypeReference<List<EmployeeParsed>>() {
            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return employee;
    }


    private String putParam(Object obj) {
        return obj != null ? obj.toString() : null;
    }
}
