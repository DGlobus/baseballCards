package com.dasha.service;

import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Contacts;
import com.dasha.model.Employee;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import com.dasha.repository.EmployeeRepository;
import com.dasha.repository.PostRepository;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.employee.params.SearchEmployeeParams;
import com.dasha.service.employee.params.UpdateEmployeeParamsForService;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresDataTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@EnablePostgresDataTest
public class EmployeeServiceTest {
    Post post = new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer");
    List<Employee> employees = List.of(
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
                    .build(),

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
                    .build()
                                      );
    CreateEmployeeParams parameter = CreateEmployeeParams.builder()
                                                         .firstName("Ivan")
                                                         .lastName("Ivanov")
                                                         .characteristics(List.of("lala"))
                                                         .contacts(Contacts.builder()
                                                                           .email("ccc@aaa.aa")
                                                                           .phone("111-00-00")
                                                                           .build())
                                                         .jobType(JobType.FULL_TIME)
                                                         .build();
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private PostRepository repositoryPost;

    @BeforeEach
    void setup() {
        repositoryPost.save(post);
        employeeService = new EmployeeService(repository);
    }

    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void create() {
        //arrange
        Employee expected = employees.get(0);

        //act
        Employee actual = employeeService.create(parameter, post);

        //assert
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
        assertTrue(repository.existsById(actual.getId()));
    }

    @Test
    void getById() {
        //arrange
        UUID id = createEmployeeInRepository(employees.get(0));
        Employee expected = employees.get(0);
        expected.setId(id);

        //act
        Employee actual = employeeService.getById(id);

        //assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getIdNotExist() {
        //assert
        UUID id = UUID.randomUUID();
        //act
        ItemNotFoundException ex = assertThrows(ItemNotFoundException.class, () -> {employeeService.getById(id);});

        String expected = "Данного работника не существует " + id;
        String actual = ex.getMessage();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        //arrange
        List<Employee> expected = employees;
        createTwoEmployeesInRepository();

        //act
        List<Employee> actual = employeeService.getAll(new SearchEmployeeParams("", null), true);

        //assert
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void update() {
        //arrange
        UUID id = createEmployeeInRepository(employees.get(0));
        Employee expected = employees.get(0);

        UpdateEmployeeParamsForService params = UpdateEmployeeParamsForService.builder()
                                                                              .id(id)
                                                                              .firstName("Denis")
                                                                              .lastName("Denisov")
                                                                              .characteristics(List.of("nana"))
                                                                              .post(post)
                                                                              .contacts(Contacts.builder()
                                                                                                .phone("111-11-11")
                                                                                                .email("bbb@bb.bb")
                                                                                                .workEmail("work@all.day")
                                                                                                .build())
                                                                              .jobType(JobType.PART_TIME)
                                                                              .build();
        setExpectedNewValue(expected);
        expected.setId(id);

        //act
        employeeService.update(params);
        Employee actual = repository.findById(id).orElse(null);

        //assert
        assertEquals(actual, expected);
    }

    private void setExpectedNewValue(Employee expected) {
        expected.setFirstName("Denis");
        expected.setLastName("Denisov");
        expected.setCharacteristics(List.of("nana"));
        expected.setPost(post);
        expected.getContacts().setEmail("bbb@bb.bb");
        expected.getContacts().setPhone("111-11-11");
        expected.getContacts().setWorkEmail("work@all.day");
        expected.setJobType(JobType.PART_TIME);
        expected.setDescription(null);
    }

    @Test
    void delete() {
        //arrange
        UUID id = createEmployeeInRepository(employees.get(0));

        //act
        employeeService.delete(id);

        //assert
        assertThrows(ItemNotFoundException.class, () -> {employeeService.getById(id);});
        assertFalse(repository.existsById(id));
    }

    private void createTwoEmployeesInRepository() {
        Employee emp = employees.get(0);
        emp.setId(UUID.randomUUID());
        repository.save(emp);
        emp = employees.get(1);
        emp.setId(UUID.randomUUID());
        repository.save(emp);
    }

    private UUID createEmployeeInRepository(Employee employee) {
        Employee emp = employees.get(0);
        emp.setId(UUID.randomUUID());
        return repository.save(emp).getId();
    }
}
