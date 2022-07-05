package com.dasha.test.controller;

import com.dasha.controller.post.dto.PostDto;
import com.dasha.controller.post.dto.CreatePostDto;
import com.dasha.controller.post.mapper.PostMapper;
import com.dasha.controller.post.mapper.PostMapperImpl;
import com.dasha.exceptions.ErrorDetails;
import com.dasha.model.JobType;
import com.dasha.model.Post;
import com.dasha.service.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private final PostService postService = new PostService();

    @Autowired
    private final PostMapper mapper = new PostMapperImpl();
    
    private CreatePostDto post;

    @BeforeEach
    private void setup() {
        post = new CreatePostDto("senior developer");
    }

    private PostDto createPostInService(CreatePostDto post) {
        return mapper.toDto(postService.create(mapper.toParams(post)));
    }


    @Test
    void create() {
        //arrange
        PostDto expected = mapper.toDto(post);

        //act
        PostDto actual = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/post/create")
                        .build())
                .bodyValue(post)
                .exchange()
                .expectStatus()
                //assert
                .isCreated()
                .expectBody(PostDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void update() {
        //arrange
        PostDto expected = createPostInService(post);
        expected.setName("anotherone");

        CreatePostDto updateDto = new CreatePostDto("anotherone");

        PostDto actual = webTestClient.post()
                .uri("/api/post/update/" + expected.getId())
                .bodyValue(updateDto)
                .exchange()
                //act
                .expectStatus()
                .isOk()
                .expectBody(PostDto.class)
                .returnResult()
                .getResponseBody();

        //assert
        assertEquals(actual, expected);
    }

    @Test
    void updateIdIfNotExist() {
        //arrange
        UUID postId = UUID.randomUUID();
        CreatePostDto updateDto = new CreatePostDto("lala");
        ErrorDetails errorDetails = new ErrorDetails("Данной должности не существует " + postId);

        //act
        webTestClient.post()
                .uri("/api/post/update/" + postId)
                .bodyValue(updateDto)
                .exchange()
                //assert
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorDetails.class)
                .isEqualTo(errorDetails);
    }

    @Test
    void getById() {
        //arrange
        PostDto expected = createPostInService(post);

        //act
        PostDto actual = webTestClient.post()
                .uri("/api/post/getById/" + expected.getId())
                .exchange()
                //assert
                .expectStatus()
                .isOk()
                .expectBody(PostDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(actual, expected);
    }

    @Test
    void getByIdIfNotExist() {
        //arrange
        UUID postId = UUID.randomUUID();
        ErrorDetails errorDetails = new ErrorDetails("Данной должности не существует " + postId);

        //act
        webTestClient.post()
                .uri("/api/post/getById/" + postId)
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
        List<PostDto> expected = getAllFromService();

        //act
        List<PostDto> actual = getAllPosts();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        //arrange
        List<PostDto> postDtos = getAllFromService();

        //act
        webTestClient.post()
                .uri("/api/post/delete/" + postDtos.get(0).getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .returnResult();

        List<PostDto> actual = getAllPosts();

        postDtos.remove(0);

        //assert
        assertEquals(postDtos, actual);
    }


    private List<PostDto> getAllFromService() {
        return mapper.toDto(postService.getAll());
    }

    private List<PostDto> getAllPosts() {
        return webTestClient.post()
                .uri("/api/post/getAll")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PostDto.class)
                .returnResult()
                .getResponseBody();
    }
}
