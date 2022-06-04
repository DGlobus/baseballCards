package com.dasha;

import com.dasha.model.Employee;
import com.dasha.service.EmployeeService.EmployeeService;
import com.dasha.service.getCards.ParseEmployeeFile;
import com.dasha.service.getCards.ParseEmployeeJson;
import com.dasha.service.EmployeeService.SearchParametrs;
import com.dasha.service.printCards.PrintToConsole;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        if(args.length == 0)
            throw new IllegalArgumentException("There is no imparted parameters");
        String path = args[0];

        SearchParametrs searchParametrs = getSearchParametrs(args);


        //ParseEmployeeFile getCards = new ParseEmployeeTxt();
        ParseEmployeeFile getCards= new ParseEmployeeJson();
        List<Employee> employees = getCards.read(path);
        EmployeeService employeeService = new EmployeeService();
        employees = employeeService.filter(employees, searchParametrs);
        employees = employeeService.sort(employees);

        PrintToConsole print= new PrintToConsole(employees);
        print.print();
    }

    private static SearchParametrs getSearchParametrs(String[] args) {
        if(args.length > 1){
            SearchParametrs params = new SearchParametrs();
            for(int i = 1; i<args.length; i++){
                if(args[i].contains("-name")){
                    params.setName(parseParametr(args[i]));
                }
                if(args[i].contains("-id")){
                    params.setId(UUID.fromString(parseParametr(args[i])));
                }
            }
            return params;
        }
        else{
            return null;
        }
    }

    private static String parseParametr(String param){
        int indexOfEqualsSing = param.indexOf("=");
        return param.substring(indexOfEqualsSing + 1).toLowerCase(Locale.ROOT);
    }
}
