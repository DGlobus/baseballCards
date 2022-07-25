package com.dasha.controller;

import com.dasha.action.CreateEmployeeAction;
import com.dasha.controller.employee.dto.ContactsDto;
import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.exceptions.ErrorDetails;
import com.dasha.model.Contacts;
import com.dasha.model.Employee;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import com.dasha.repository.EmployeeRepository;
import com.dasha.repository.PostRepository;
import com.dasha.service.employee.EmployeeService;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
public class EmployeeControllerIT {
    Post middleDeveloper = new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer");
    Post student = new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student");
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CreateEmployeeAction createAction;
    @Autowired
    private EmployeeMapper mapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setup() {
        postRepository.save(middleDeveloper);
        postRepository.save(student);
    }

    @AfterEach
    void clean() {
        employeeRepository.deleteAll();
    }


    @Test
    void create() {
        //arrange
        Employee expected = Employee.builder()
                                    .firstName("Vasya")
                                    .lastName("Vasilev")
                                    .characteristics(List.of("lala"))
                                    .description("ttt")
                                    .post(student)
                                    .contacts(Contacts.builder()
                                                      .email("aaa@aaa.aa")
                                                      .phone("000-00-00")
                                                      .build())
                                    .jobType(JobType.CONTRACT)
                                    .build();


        //act
        EmployeeDto actual = webTestClient.post()
                                          .uri("/api/employee/create")
                                          .bodyValue(CreateEmployeeDto.builder()
                                                                      .firstName("Vasya")
                                                                      .lastName("Vasilev")
                                                                      .characteristics(List.of("lala"))
                                                                      .description("ttt")
                                                                      .postId(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"))
                                                                      .contacts(ContactsDto.builder()
                                                                                           .email("aaa@aaa.aa")
                                                                                           .phone("000-00-00")
                                                                                           .build())
                                                                      .jobType(JobType.CONTRACT)
                                                                      .build())
                                          .exchange()
                                          .expectStatus()
                                          //assert
                                          .isCreated()
                                          .expectBody(EmployeeDto.class)
                                          .returnResult()
                                          .getResponseBody();

        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
        assertNotNull(actual.getId());
        assertTrue(employeeRepository.existsById(actual.getId()));
    }

    @Test
    void createFromFile() {
        //arrange
        List<EmployeeDto> expected = new ArrayList<>();
        {
            expected.add(EmployeeDto.builder()
                                    .firstName("Геннадий")
                                    .lastName("Кузьмин")
                                    .description("Lorem ipsum")
                                    .characteristics(List.of("honest", "introvert", "like criticism", "love of Learning", "pragmatism"))
                                    .post(middleDeveloper)
                                    .contacts(ContactsDto.builder()
                                                         .email("aaa@aa.aa")
                                                         .phone("111-111")
                                                         .build())
                                    .jobType(JobType.PART_TIME)
                                    .build());

            expected.add(EmployeeDto.builder()
                                    .firstName("Дарья")
                                    .lastName("Глобчастая")
                                    .description("Big Harry Potter Fan")
                                    .characteristics(List.of("introvert"))
                                    .post(student)
                                    .contacts(ContactsDto.builder()
                                                         .email("bbb@bb.bb")
                                                         .phone("222-222")
                                                         .build())
                                    .jobType(JobType.TEMPORARY)
                                    .build());
        }

        //act
        List<EmployeeDto> actual = webTestClient.post()
                                                .uri("/api/employee/createFromLocalFile")
                                                .bodyValue(new File(EmployeeControllerIT.class.getClassLoader().getResource("cards.json").getFile()))
                                                .exchange()
                                                .expectStatus()
                                                //assert
                                                .isCreated()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();

        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
        assertNotNull(actual.get(0).getId());
        assertNotNull(actual.get(1).getId());
        assertTrue(employeeRepository.existsById(actual.get(0).getId()));
        assertTrue(employeeRepository.existsById(actual.get(1).getId()));
    }

    @Test
    void getById() {
        //arrange
        Employee expected = getEmployeeVasya();
        addEmployeeInRepository(List.of(expected));

        //act
        EmployeeDto actual = webTestClient.get()
                                          .uri("/api/employee/{id}", expected.getId())
                                          .exchange()
                                          //assert
                                          .expectStatus()
                                          .isOk()
                                          .expectBody(EmployeeDto.class)
                                          .returnResult()
                                          .getResponseBody();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private Employee getEmployeeVasya() {
        return Employee.builder()
                       .id(UUID.randomUUID())
                       .firstName("Vasya")
                       .lastName("Vasilev")
                       .characteristics(List.of("lala"))
                       .description("ttt")
                       .post(student)
                       .contacts(Contacts.builder()
                                         .email("aaa@aaa.aa")
                                         .phone("000-00-00")
                                         .build())
                       .jobType(JobType.CONTRACT)
                       .build();
    }

    private void addEmployeeInRepository(List<Employee> employees) {
        employees.forEach(emp -> employeeRepository.save(emp));
    }

    @Test
    void getByIdIfNotExist() {
        //arrange
        UUID employeeId = UUID.randomUUID();
        ErrorDetails errorDetails = new ErrorDetails("Данного работника не существует " + employeeId);

        //act
        webTestClient.get()
                     .uri("/api/employee/{id}", employeeId)
                     .exchange()
                     //assert
                     .expectStatus()
                     .isNotFound()
                     .expectBody(ErrorDetails.class)
                     .isEqualTo(errorDetails);
    }

    @Test
    void getAll() {
        //arrange
        List<Employee> expected = createTwoEmployees();
        addEmployeeInRepository(expected);

        //act
        List<EmployeeDto> actual = getAllEmployees();

        //assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private List<Employee> createTwoEmployees() {
        List<Employee> list = new ArrayList<>();
        list.add(Employee.builder()
                         .id(UUID.randomUUID())
                         .firstName("Ivan")
                         .lastName("Ivanov")
                         .characteristics(List.of("nana"))
                         .post(middleDeveloper)
                         .contacts(Contacts.builder()
                                           .email("ccc@aaa.aa")
                                           .phone("111-00-00")
                                           .build())
                         .jobType(JobType.CONTRACT)
                         .build()
                );
        list.add(getEmployeeVasya());

        return list;
    }

    @Test
    void update() {
        //arrange
        Employee expected = getEmployeeVasya();
        addEmployeeInRepository(List.of(expected));

        UpdateEmployeeDto updateDto = getUpdateDto();

        setExpectedNewValue(expected);

        EmployeeDto actual = webTestClient.post()
                                          .uri("/api/employee/{id}/update", expected.getId())
                                          .bodyValue(updateDto)
                                          .exchange()
                                          //act
                                          .expectStatus()
                                          .isOk()
                                          .expectBody(EmployeeDto.class)
                                          .returnResult()
                                          .getResponseBody();

        //assert
        Employee newExpected = employeeRepository.findById(expected.getId()).orElse(null);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        assertThat(newExpected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void updateIdIfNotExist() {
        //arrange
        UUID employeeId = UUID.randomUUID();
        UpdateEmployeeDto updateDto = getUpdateDto();
        ErrorDetails errorDetails = new ErrorDetails("Данного работника не существует " + employeeId);

        //act
        webTestClient.post()
                     .uri("/api/employee/{id}/update", employeeId)
                     .bodyValue(updateDto)
                     .exchange()
                     //assert
                     .expectStatus()
                     .isNotFound()
                     .expectBody(ErrorDetails.class)
                     .isEqualTo(errorDetails);
    }

    private UpdateEmployeeDto getUpdateDto() {
        return UpdateEmployeeDto.builder()
                                .firstName("Denis")
                                .lastName("Denisov")
                                .characteristics(List.of("nana"))
                                .postId(middleDeveloper.getId())
                                .contacts(ContactsDto.builder()
                                                     .phone("111-11-11")
                                                     .email("bbb@bb.bb")
                                                     .workEmail("work@all.day")
                                                     .build())
                                .jobType(JobType.PART_TIME)
                                .build();
    }


    private void setExpectedNewValue(Employee expected) {
        expected.setFirstName("Denis");
        expected.setLastName("Denisov");
        expected.setCharacteristics(List.of("nana"));
        expected.setPost(middleDeveloper);
        expected.getContacts().setEmail("bbb@bb.bb");
        expected.getContacts().setPhone("111-11-11");
        expected.getContacts().setWorkEmail("work@all.day");
        expected.setJobType(JobType.PART_TIME);
        expected.setDescription(null);
    }

    @Test
    void delete() {
        //arrange
        List<Employee> expected = createTwoEmployees();
        addEmployeeInRepository(expected);

        //act
        webTestClient.post()
                     .uri("/api/employee/{id}/delete", expected.get(0).getId())
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody()
                     .returnResult();

        List<Employee> actual = employeeService.getAll(null, true);

        expected.remove(0);

        //assert
        assertEquals(expected, actual);
    }


    private List<EmployeeDto> getAllEmployees() {
        return webTestClient.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path("/api/employee/getAll")
                                    .build())
                            .exchange()
                            .expectStatus()
                            .isOk()
                            .expectBodyList(EmployeeDto.class)
                            .returnResult()
                            .getResponseBody();
    }

}
