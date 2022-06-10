package com.dasha.util.ioutils.parse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ParseEmployeeJson implements ParseEmployeeFile {

    private final List<EmployeeParsed> employee = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<EmployeeParsed> read(String path) {

        try {
            var list = mapper.readValue(new File(path), ArrayList.class);

            for(var emp:list){
                employee.add(parseEmployee(emp));
            }
        } catch (IOException e) {
            e.getMessage();
        }

        return employee;
    }


    private EmployeeParsed parseEmployee(Object employeeObj) {
        LinkedHashMap employee = (LinkedHashMap) employeeObj;

        UUID postId = UUID.fromString(putParam(employee.get("postId")));

        return EmployeeParsed.builder()
                                        .firstName(putParam(employee.get("firstName")))
                                        .lastName(putParam(employee.get("lastName")))
                                        .description(putParam(employee.get("description")))
                                        .characteristics((List<String>) employee.get("characteristics"))
                                        .postId(postId)
                                        .email(putParam(employee.get("email")))
                                        .phone(putParam(employee.get("phone")))
                                        .workEmail(putParam(employee.get("workEmail")))
                                .build();

    }

    private String putParam(Object obj){
        return obj != null ? obj.toString() : null;
    }
}
