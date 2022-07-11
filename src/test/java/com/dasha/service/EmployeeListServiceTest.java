package com.dasha.service;

import com.dasha.model.Employee;
import com.dasha.model.Post;
import com.dasha.service.employee.EmployeeListService;
import com.dasha.service.employee.params.SearchEmployeeParams;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EmployeeListServiceTest {

    private final Map<UUID, Post> map= fillMap();

    private Map<UUID, Post> fillMap() {
        Map<UUID, Post> posts = new HashMap<>();
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer"));
        posts.put(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student"));
        posts.put(UUID.fromString("399878e4-84fc-452b-968f-4209ae4c19a8"), new Post(UUID.fromString("399878e4-84fc-452b-968f-4209ae4c19a8"), "random person"));
        return posts;
    }

    private final EmployeeListService employeeService = new EmployeeListService();

    private final Employee Gennadiy = Employee.builder()
                .firstName("Геннадий")
                .lastName("Кузьмин")
                .description("Lorem ipsum")
                .characteristics(List.of("honest", "introvert", "like criticism", "love of Learning", "pragmatism"))
                .post(map.get(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707")))
                .build();

    private final Employee Daria = Employee.builder()
                .firstName("Дарья")
                .lastName("Глобчастая")
                .description("Big Harry Potter Fan")
                .characteristics(List.of("introvert"))
                .post(map.get(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")))
                .build();

    private final Employee Ivan = Employee.builder()
                .firstName("Иван")
                .lastName("Петров")
                .description("бла бла")
                .characteristics(List.of("бла", "бла бла бла"))
                .post(map.get(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")))
                .build();

    private final Employee Petr = Employee.builder()
                .firstName("Петя")
                .lastName("Иванов")
                .description("алаллала")
                .characteristics(List.of("ла", "дада"))
                .post(map.get(UUID.fromString("399878e4-84fc-452b-968f-4209ae4c19a8")))
                .build();

    private final List<Employee> allEmployees = List.of(Gennadiy, Daria, Ivan, Petr);

    @Test
    public void filterNameParametr(){
        //arrange
        List<Employee> expected = List.of(Ivan, Petr);

        SearchEmployeeParams searchParametrs = new SearchEmployeeParams("иван", null);

        //act
        List<Employee> actual = employeeService.filter(allEmployees, searchParametrs);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void filterIdParametr(){
        //arrange
        List<Employee> expected = List.of(Daria, Ivan);

        SearchEmployeeParams searchParametrs = new SearchEmployeeParams(null, UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"));

        //act
        List<Employee> actual = employeeService.filter(allEmployees, searchParametrs);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void sort(){
        //arrange
        List<Employee> expected = List.of(Daria, Petr, Gennadiy, Ivan);

        //act
        List<Employee> actual = employeeService.sort(allEmployees);

        //assert
        assertEquals(expected, actual);
    }
}