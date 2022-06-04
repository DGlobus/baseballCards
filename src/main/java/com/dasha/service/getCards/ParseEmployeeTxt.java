package com.dasha.service.getCards;

import com.dasha.model.Employee;
import com.dasha.model.PostMap;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ParseEmployeeTxt implements ParseEmployeeFile {
    private List<Employee> employees;
    private PostMap map = new PostMap();

    @Override
    public List<Employee> read(String path) {
        employees = new ArrayList<>();

        File file = new File(path);
        try (FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader)){

            String currentLine = bufferedReader.readLine();

            while(currentLine != null){
                Employee employee = Employee.builder()
                        .firstName(getSubstring(currentLine))
                        .lastName(getSubstring(bufferedReader.readLine()))
                        .description(getSubstring(bufferedReader.readLine()))
                        .characteristics(getSubstringList(bufferedReader.readLine()))
                        .post(map.getPosts().get(UUID.fromString(getSubstring(bufferedReader.readLine()))))
                        .build();

                employees.add(employee);
                currentLine = bufferedReader.readLine()!=null?bufferedReader.readLine():null;
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return employees;
    }

    private List<String> getSubstringList(String readLine) {
        return Arrays.stream(readLine.split(",")).map(String::trim).toList();
    }

    private String getSubstring(String currentString){
        int colonIndex = currentString.indexOf(':')+1;
        return currentString.substring(colonIndex).trim();
    }

}
