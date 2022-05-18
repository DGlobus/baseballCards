package com.dasha.getCards;

import com.dasha.Employee;
import com.dasha.Post;
import com.dasha.PostMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetCardsFromTxt implements GetCards {
    private List<Employee> employees;
    private PostMap map = new PostMap();

    @Override
    public List<Employee> read(String path) {
        employees = new ArrayList<>();

        try{
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLine = bufferedReader.readLine();
            while(currentLine != null){
                Employee employee = new Employee();
                employee.setFirstName(getSubstring(currentLine));
                employee.setLastName(getSubstring(bufferedReader.readLine()));
                employee.setDescription(getSubstring(bufferedReader.readLine()));
                employee.setCharacteristics(getSubstringList(bufferedReader.readLine()));
                employee.setPost((Post)map.posts.get(UUID.fromString(getSubstring(bufferedReader.readLine()))));
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
        List<String> characteres = Arrays.stream(readLine.split(",")).toList();
        characteres.stream().forEach(a -> a.trim());
        return characteres;
    }

    private String getSubstring(String currentString){
        int colonIndex = currentString.indexOf(':')+1;
        return currentString.substring(colonIndex).trim();
    }

}
