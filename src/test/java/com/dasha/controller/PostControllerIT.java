package com.dasha.controller;

import com.dasha.controller.post.dto.PostDto;
import com.dasha.controller.post.dto.CreatePostDto;
import com.dasha.controller.post.mapper.PostMapper;
import com.dasha.exceptions.ErrorDetails;
import com.dasha.model.Post;
import com.dasha.service.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.lang.reflect.Field;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper mapper;
    
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
    void getById() {
        //arrange
        Post expected = new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student");

        //act
        PostDto actual = webTestClient.post()
                .uri("/api/post/{id}", expected.getId())
                .exchange()
                //assert
                .expectStatus()
                .isOk()
                .expectBody(PostDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void getByIdIfNotExist() {
        //arrange
        UUID postId = UUID.randomUUID();
        ErrorDetails errorDetails = new ErrorDetails("Данной должности не существует " + postId);

        //act
        webTestClient.post()
                .uri("/api/post/{id}", postId)
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
        List<Post> expected = getAllFromService();

        //act
        List<PostDto> actual = getAllPosts();

        //assert
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }


    @Test
    void update() {
        //arrange
        PostDto expected = createPostInService(post);
        expected.setName("anotherone");

        CreatePostDto updateDto = new CreatePostDto("anotherone");

        PostDto actual = webTestClient.post()
                .uri("/api/post/{id}/update/", expected.getId())
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
                .uri("/api/post/{id}/update/", postId)
                .bodyValue(updateDto)
                .exchange()
                //assert
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorDetails.class)
                .isEqualTo(errorDetails);
    }

    @Test
    void delete() {
        //arrange
        List<Post> posts = getAllFromService();

        //act
        webTestClient.post()
                .uri("/api/post/{id}/delete/", posts.get(0).getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .returnResult();

        List<Post> actual = getAllFromService();

        posts.remove(0);

        //assert
        assertThat(posts).usingRecursiveComparison().isEqualTo(actual);
    }


    private List<Post> getAllFromService() {
        List<Post> list = new ArrayList<>();
        try{
            Field field = postService.getClass().getDeclaredField("posts");
            field.setAccessible(true);
            Map<UUID, Post> map = (Map<UUID, Post>) field.get(postService);
            list = new ArrayList<>(map.values());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
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
