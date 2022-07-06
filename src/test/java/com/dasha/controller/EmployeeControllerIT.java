package com.dasha.controller;

import com.dasha.action.CreateAction;
import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.exceptions.ErrorDetails;
import com.dasha.model.Contacts;
import com.dasha.model.JobType;
import com.dasha.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    private CreateEmployeeDto vasya;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CreateAction createAction;

    @Autowired
    private EmployeeMapper mapper;

    @BeforeEach
    private void setup(){
        vasya = CreateEmployeeDto.builder()
                .firstName("Vasya")
                .lastName("Vasilev")
                .characteristics(List.of("lala"))
                .description("ttt")
                .postId(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"))
                .contacts(Contacts.builder()
                        .email("aaa@aaa.aa")
                        .phone("000-00-00")
                        .build())
                .jobType(JobType.CONTRACT)
                .build();
    }


    @Test
    void create() {
        //arrange
        EmployeeDto expected = mapper.toDto(vasya);

        //act
        EmployeeDto actual = webTestClient.post()
                .uri("/api/employee/create")
                .bodyValue(vasya)
                .exchange()
                .expectStatus()
                //assert
                .isCreated()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "post").isEqualTo(expected);
    }

    @Test
    void createFromFile(){
        //arrange
        List<EmployeeDto> expected = new ArrayList<>();
        {
            expected.add(EmployeeDto.builder()
                    .firstName("Геннадий")
                    .lastName("Кузьмин")
                    .description("Lorem ipsum")
                    .characteristics(List.of("honest", "introvert", "like criticism", "love of Learning", "pragmatism"))
                    .build());

            expected.add(EmployeeDto.builder()
                    .firstName("Дарья")
                    .lastName("Глобчастая")
                    .description("Big Harry Potter Fan")
                    .characteristics(List.of("introvert"))
                    .build());
        }

        //act
        List<EmployeeDto> actual = webTestClient.post()
                .uri("/api/employee/create/fromFile")
                .bodyValue(new File(EmployeeControllerIT.class.getClassLoader().getResource("cards.json").getFile()))
                .exchange()
                .expectStatus()
                //assert
                .isCreated()
                .expectBodyList(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "post", "contacts").isEqualTo(expected);
    }

    @Test
    void getById(){
        //arrange
        EmployeeDto expected = createEmployeeInService(vasya);

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

        assertEquals(actual, expected);
    }

    @Test
    void getByIdIfNotExist(){
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
    void getAll(){
        //arrange

        List<EmployeeDto> expected = createTwoEmployeeInService();

        //act
        List<EmployeeDto> actual = getAllEmployees();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    void update(){
        //arrange
        EmployeeDto expected = createEmployeeInService(vasya);

        UpdateEmployeeDto updateDto = getUpdateDto();

        expected.getContacts().setEmail("bbb@bb.bb");
        expected.getContacts().setPhone("111-11-11");
        expected.setDescription(null);

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
        assertEquals(actual, expected);
    }

    @Test
    void updateIdIfNotExist(){
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
                .firstName("Vasya")
                .lastName("Vasilev")
                .characteristics(List.of("lala"))
                .postId(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"))
                .contacts(Contacts.builder()
                        .phone("111-11-11")
                        .email("bbb@bb.bb")
                        .build())
                .jobType(JobType.CONTRACT)
                .build();
    }

    @Test
    void delete(){
        //arrange
        List<EmployeeDto> employeeDtos = createTwoEmployeeInService();

        //act
        webTestClient.post()
                .uri("/api/employee/{id}/delete", employeeDtos.get(0).getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .returnResult();

        List<EmployeeDto> actual = getAllEmployees();

        employeeDtos.remove(0);

        //assert
        assertEquals(employeeDtos, actual);
    }

    private EmployeeDto createEmployeeInService(CreateEmployeeDto employee){
        return mapper.toDto(createAction.create(mapper.toParams(employee)));
    }


    private List<EmployeeDto> createTwoEmployeeInService() {
        createEmployeeInService(vasya);

        CreateEmployeeDto ivan = CreateEmployeeDto.builder()
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .characteristics(List.of("nana"))
                        .postId(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"))
                        .contacts(Contacts.builder()
                                .email("ccc@aaa.aa")
                                .phone("111-00-00")
                                .build())
                        .jobType(JobType.CONTRACT)
                        .build();

        createEmployeeInService(ivan);

        return new ArrayList<>(mapper.toListDto(employeeService.getAll(null, false)));
    }

    private List<EmployeeDto> getAllEmployees(){
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/employee/getAll")
                        .queryParam("isSorting", false)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(EmployeeDto.class)
                .returnResult()
                .getResponseBody();
    }

}
