package com.dasha;

import com.dasha.util.ioutils.parse.EmployeeParsed;
import com.dasha.util.ioutils.parse.ParseEmployeeJson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseEmployeeJsonTest {

    private final List<EmployeeParsed> employees = new ArrayList<>();
    {
        employees.add(EmployeeParsed.builder()
                .firstName("Геннадий")
                .lastName("Кузьмин")
                .description("Lorem ipsum")
                .characteristics(List.of("honest", "introvert", "like criticism", "love of Learning", "pragmatism"))
                .postId(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"))
                .build());

        employees.add(EmployeeParsed.builder()
                .firstName("Дарья")
                .lastName("Глобчастая")
                .description("Big Harry Potter Fan")
                .characteristics(List.of("introvert"))
                .postId(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"))
                .build());
    }


    @Test
    public void readTest(){
        //act
        List<EmployeeParsed> employeeFromMethod = new ParseEmployeeJson().read(ParseEmployeeJsonTest.class.getClassLoader().getResource("cards.json").getPath());
        //assert
        var parsed = employeeFromMethod.get(0);
        assertEquals(employees, employeeFromMethod);
    }
}