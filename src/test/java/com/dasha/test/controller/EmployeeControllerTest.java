package com.dasha.test.controller;

import com.dasha.controller.employee.dto.CreateEmployeeDto;
import com.dasha.controller.employee.dto.EmployeeDto;
import com.dasha.model.JobType;
import com.dasha.service.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PostService postService;


    @Test
    void create() {
        //arrange
        CreateEmployeeDto createDto = new CreateEmployeeDto("Vasya",
                "Vasilev",
                null,
                List.of("lala"),
                UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"),
                "aaa@aaa.aa",
                "000-00-00",
                null,
                JobType.CONTRACT.toString());


        EmployeeDto employeeDto = webTestClient.post()
                                                .uri(uriBuilder -> uriBuilder.path("/api/employee/create")
                                                        .build())
                                                .bodyValue(createDto)
                                                .exchange()
                                                //act
                                                .expectStatus()
                                                .isCreated()
                                                .expectBody(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();
        //assert
        assert employeeDto != null;
        checkEquals(createDto, employeeDto);
    }

    private void checkEquals(CreateEmployeeDto createDto, EmployeeDto employeeDto) {
        assertEquals(createDto.getFirstName(), employeeDto.getFirstName());
        assertEquals(createDto.getLastName(), employeeDto.getLastName());
        assertEquals(createDto.getDescription(), employeeDto.getDescription());
        assertEquals(createDto.getCharacteristics(), employeeDto.getCharacteristics());
        assertEquals(createDto.getPostId(), employeeDto.getPost().getId());
        assertEquals(createDto.getEmail(), employeeDto.getContacts().getEmail());
        assertEquals(createDto.getPhone(), employeeDto.getContacts().getPhone());
        assertEquals(createDto.getWorkEmail(), employeeDto.getContacts().getWorkEmail());
        assertEquals(createDto.getJobType().toLowerCase(Locale.ROOT), employeeDto.getJobType().toString().toLowerCase(Locale.ROOT));
    }
}
