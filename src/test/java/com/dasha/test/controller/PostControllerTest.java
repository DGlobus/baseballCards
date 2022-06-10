package com.dasha.test.controller;

import com.dasha.controller.post.dto.CreatePostDto;
import com.dasha.controller.post.dto.PostDto;
import com.dasha.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {
    private final CreatePostDto createDto = new CreatePostDto();
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PostService postService;

    @Test
    void post() {
        //arrange
        createDto.setName("Tech Writer");

        PostDto postDto = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/post/create")
                        .build())
                .bodyValue(createDto)
                .exchange()
                //act
                .expectStatus()
                .isCreated()
                .expectBody(PostDto.class)
                .returnResult()
                .getResponseBody();
        //assert
        Assertions.assertEquals(createDto.getName(), postDto.getName());
    }
}
