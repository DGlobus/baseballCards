package com.dasha.service;

import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Contacts;
import com.dasha.model.Employee;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.employee.params.SearchEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    Post post = new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer");
    List<Employee> employees = List.of(
            Employee.builder()
                    .firstName("Vasya")
                    .lastName("Vasilev")
                    .characteristics(List.of("lala"))
                    .post(post)
                    .contacts(Contacts.builder()
                            .email("aaa@aaa.aa")
                            .phone("000-00-00")
                            .build())
                    .jobType(JobType.CONTRACT)
                    .build(),

     Employee.builder()
            .firstName("Ivan")
            .lastName("Ivanov")
            .characteristics(List.of("lala"))
            .post(post)
            .contacts(Contacts.builder()
                    .email("ccc@aaa.aa")
                    .phone("111-00-00")
                    .build())
            .jobType(JobType.FULL_TIME)
            .build()
    );

    List<CreateEmployeeParams> params = List.of(
            CreateEmployeeParams.builder()
                    .firstName("Vasya")
                    .lastName("Vasilev")
                    .characteristics(List.of("lala"))
                    .contacts(Contacts.builder()
                            .email("aaa@aaa.aa")
                            .phone("000-00-00")
                            .build())
                    .jobType(JobType.CONTRACT)
                    .build(),

            CreateEmployeeParams.builder()
            .firstName("Ivan")
            .lastName("Ivanov")
            .characteristics(List.of("lala"))
            .contacts(Contacts.builder()
                    .email("ccc@aaa.aa")
                    .phone("111-00-00")
                    .build())
            .jobType(JobType.FULL_TIME)
            .build()
    );
    List<UUID> ids = new ArrayList<>();

    @BeforeEach
    void setup(){
        employeeService = new EmployeeService();
    }

    @Test
    void create(){
        //arrange
        Employee expected = employees.get(0);

        //act
        Employee actual = employeeService.create(params.get(0), post);
        ids.add(actual.getId());

        //assert
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void getById(){
        //arrange
        UUID id = createEmployeeInService(params.get(0));
        Employee expected = employees.get(0);

        //act
        Employee actual = employeeService.getById(id);

        //assert
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void getIdNotExist(){
        UUID id = UUID.randomUUID();
        ItemNotFoundException ex = assertThrows(ItemNotFoundException.class, () -> {employeeService.getById(id);});

        String expected = "Данного работника не существует " + id;
        String actual = ex.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void getAll(){
        //arrange
        List<Employee> expected = employees;
        createTwoEmployeesInService();

        //act
        List<Employee> actual = employeeService.getAll(new SearchEmployeeParams("", null), false);

        //assert
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void update(){
        //arrange
        UUID id = createEmployeeInService(params.get(0));
        Employee expected = employees.get(0);

        UpdateEmployeeParams params = UpdateEmployeeParams.builder()
                .firstName("Vasya")
                .lastName("Petrov")
                .characteristics(List.of("lala"))
                .post(post)
                .contacts(Contacts.builder()
                        .email("aaa@aaa.aa")
                        .phone("000-00-00")
                        .build())
                .jobType(JobType.CONTRACT)
                .build();
        expected.setLastName("Petrov");
        expected.setId(id);

        //act
        Employee actual = employeeService.update(id, params);

        //assert
        assertEquals(actual, expected);
    }

    @Test
    void delete(){
        //arrange
        UUID id = createEmployeeInService(params.get(0));

        //act
        employeeService.delete(id);

        //assert
        assertThrows(ItemNotFoundException.class, () -> {employeeService.getById(id);});
    }

    private void createTwoEmployeesInService() {
        ids.add(employeeService.create(params.get(0), post).getId());
        ids.add(employeeService.create(params.get(1), post).getId());
    }

    private UUID createEmployeeInService(CreateEmployeeParams params) {
        UUID id = employeeService.create(params, post).getId();
        ids.add(id);
        return id;
    }
}
