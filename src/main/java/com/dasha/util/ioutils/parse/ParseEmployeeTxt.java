package com.dasha.util.ioutils.parse;

import com.dasha.service.post.PostService;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ParseEmployeeTxt implements ParseEmployeeFile {
    private final PostService map;

    @Override
    public List<EmployeeParsed> read(String path) {
        List<EmployeeParsed> employees = new ArrayList<>();

        File file = new File(path);
        try (FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader)){

            String currentLine = bufferedReader.readLine();

            while(currentLine != null){
                EmployeeParsed employee = EmployeeParsed.builder()
                                                        .firstName(getSubstring(currentLine))
                                                        .lastName(getSubstring(bufferedReader.readLine()))
                                                        .description(getSubstring(bufferedReader.readLine()))
                                                        .characteristics(getSubstringList(bufferedReader.readLine()))
                                                        .postId(UUID.fromString(getSubstring(bufferedReader.readLine())))
                                                        .build();

                employees.add(employee);
                currentLine = bufferedReader.readLine()!=null?bufferedReader.readLine():null;
            }
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        return employees;
    }

    private List<String> getSubstringList(String readLine) {
        return Arrays.stream(readLine.split(",")).map(String::trim).collect(Collectors.toList());
    }

    private String getSubstring(String currentString){
        int colonIndex = currentString.indexOf(':')+1;
        return currentString.substring(colonIndex).trim();
    }

}
