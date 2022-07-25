package com.dasha.util.ioutils.parse;

import org.junit.jupiter.api.Test;

import java.io.File;
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
                                    .email("aaa@aa.aa")
                                    .phone("111-111")
                                    .jobType("PART_TIME")
                                    .build());

        employees.add(EmployeeParsed.builder()
                                    .firstName("Дарья")
                                    .lastName("Глобчастая")
                                    .description("Big Harry Potter Fan")
                                    .characteristics(List.of("introvert"))
                                    .postId(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"))
                                    .email("bbb@bb.bb")
                                    .phone("222-222")
                                    .jobType("TEMPORARY")
                                    .build());
    }

    @Test
    public void readTest() {
        //act
        List<EmployeeParsed> employeeFromMethod = new ParseEmployeeJson().read(new File(ParseEmployeeJsonTest.class.getClassLoader().getResource("cards.json").getFile()));
        //assert
        var parsed = employeeFromMethod.get(0);
        assertEquals(employees, employeeFromMethod);
    }
}