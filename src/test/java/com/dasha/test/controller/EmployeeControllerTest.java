package com.dasha.test.controller;

import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.controller.employee.dto.UpdateEmployeeDto;
import com.dasha.controller.employee.mapper.EmployeeMapper;
import com.dasha.controller.employee.mapper.EmployeeMapperImpl;
import com.dasha.exceptions.ErrorDetails;
import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import com.dasha.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    private final Map<UUID, Post> postMap = new HashMap<>();

    private CreateEmployeeDto vasya;

    @Autowired
    private final EmployeeService employeeService = new EmployeeService();

    @Autowired
    private final EmployeeMapper mapper = new EmployeeMapperImpl();


    public EmployeeControllerTest() {
        postMap.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer"));
        postMap.put(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student"));
        postMap.put(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707"), "random person"));
    }

    @BeforeEach
    private void setup(){
        vasya = new CreateEmployeeDto("Vasya",
                "Vasilev",
                null,
                List.of("lala"),
                postMap.get(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")),
                "aaa@aaa.aa",
                "000-00-00",
                null,
                JobType.CONTRACT.toString());
    }

    private EmployeeDto createEmployeeInService(CreateEmployeeDto employee){
        return mapper.toDto(employeeService.create(mapper.toParams(employee)));
    }



    @Test
    void create() {
        //arrange
        EmployeeDto expected = mapper.toDto(vasya);

        //act
        EmployeeDto actual = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/employee/create")
                        .build())
                .bodyValue(vasya)
                .exchange()
                .expectStatus()
                //assert
                .isCreated()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void update(){
        //arrange
        EmployeeDto expected = createEmployeeInService(vasya);

        UpdateEmployeeDto updateDto = new UpdateEmployeeDto(
                expected.getId(),
                null,
                null,
                null,
                null,
                null,
                "bbb@bb.bb",
                "111-11-11",
                null,
                null
        );

        expected.getContacts().setEmail("bbb@bb.bb");
        expected.getContacts().setPhone("111-11-11");

        EmployeeDto actual = webTestClient.post()
                .uri("/api/employee/update/"+ updateDto.getId())
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
        UpdateEmployeeDto updateDto = new UpdateEmployeeDto(
                employeeId,
                null,
                null,
                null,
                null,
                null,
                "bbb@bb.bb",
                "111-11-11",
                null,
                null
        );
        ErrorDetails errorDetails = new ErrorDetails("Данного работника не существует " + employeeId);

        //act
        webTestClient.post()
                .uri("/api/employee/update/"+ employeeId)
                .bodyValue(updateDto)
                .exchange()
                //assert
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorDetails.class)
                .isEqualTo(errorDetails);
    }

    @Test
    void getById(){
        //arrange
        EmployeeDto expected = createEmployeeInService(vasya);

        //act
        EmployeeDto actual = webTestClient.post()
                .uri("/api/employee/getById/"+ expected.getId())
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
        webTestClient.post()
                .uri("/api/employee/getById/"+ employeeId)
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
    void delete(){
        //arrange
        List<EmployeeDto> employeeDtos = createTwoEmployeeInService();

        //act
        webTestClient.post()
                .uri("/api/employee/delete/" + employeeDtos.get(0).getId())
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


    private List<EmployeeDto> createTwoEmployeeInService() {
        createEmployeeInService(vasya);

        CreateEmployeeDto ivan = new CreateEmployeeDto("Ivan",
                "Ivanov",
                null,
                List.of("nana"),
                postMap.get(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707")),
                "ccc@aaa.aa",
                "111-00-00",
                null,
                JobType.CONTRACT.toString());

        createEmployeeInService(ivan);

        return new ArrayList<>(mapper.toDto(employeeService.getAll(null, false)));
    }

    private List<EmployeeDto> getAllEmployees(){
        return webTestClient.post()
                .uri("/api/employee/getAll")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(EmployeeDto.class)
                .returnResult()
                .getResponseBody();
    }

}
