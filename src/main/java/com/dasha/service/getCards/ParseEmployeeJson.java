package com.dasha.service.getCards;

import com.dasha.action.FromDtoToEmployee;
import com.dasha.model.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParseEmployeeJson implements ParseEmployeeFile {

    private List<EmployeeDTO> employeesDTO = new ArrayList<>();
    private List<Employee> employee = new ArrayList<>();

    @Override
    public List<Employee> read(String path) {

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader(path)){
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;

            for(var emp : employeeList){
                employeesDTO.add(parseEmployee((JSONObject) emp));
            }
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("Невозможно преобразовать переданный файл", e);
        }
        catch (IOException e){
            throw new RuntimeException("Не удается прочитать выбранный файл", e);
        }

        for(EmployeeDTO emp :employeesDTO){
            employee.add(FromDtoToEmployee.parse(emp));
        }

        return employee;
    }


    private EmployeeDTO parseEmployee(JSONObject employeeJSON) {
        EmployeeDTO employee = new EmployeeDTO();

        employee.setFirstName(employeeJSON.get("firstName").toString());
        employee.setLastName(employeeJSON.get("lastName").toString());
        employee.setDescription(employeeJSON.get("description").toString());
        employee.setCharacteristics((List<String>)employeeJSON.get("characteristics"));
        employee.setPostId(employeeJSON.get("postId").toString());

        return employee;
    }


}
