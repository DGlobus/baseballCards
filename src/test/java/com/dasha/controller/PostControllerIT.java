package com.dasha.controller;

import com.dasha.controller.post.dto.CreatePostDto;
import com.dasha.controller.post.dto.PostDto;
import com.dasha.controller.post.dto.UpdatePostDto;
import com.dasha.controller.post.mapper.PostMapper;
import com.dasha.exceptions.ErrorDetails;
import com.dasha.model.Post;
import com.dasha.repository.PostRepository;
import com.dasha.service.post.PostService;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
public class PostControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper mapper;

    private CreatePostDto post;

    @Autowired
    private PostRepository repository;

    @BeforeEach
    private void setup() {
        post = new CreatePostDto("senior developer");
    }


    private PostDto createPostInRepository(Post post) {
        return mapper.toDto(repository.save(post));
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
        assertTrue(repository.existsById(actual.getId()));
    }


    @Test
    void getById() {
        //arrange
        Post expected = repository.findById(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")).orElse(null);

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
        List<Post> expected = getAllFromRepository();

        //act
        List<PostDto> actual = getAllPosts();

        //assert
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }


    @Test
    void update() {
        //arrange
        PostDto expected = createPostInRepository(new Post(UUID.randomUUID(), post.getName()));
        expected.setName("anotherone");

        UpdatePostDto updateDto = new UpdatePostDto("anotherone");

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
        assertThat(repository.findById(expected.getId()).orElse(null)).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void updateIdIfNotExist() {
        //arrange
        UUID postId = UUID.randomUUID();
        UpdatePostDto updateDto = new UpdatePostDto("lala");
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
        UUID id = UUID.randomUUID();
        Post removed = repository.save(new Post(id, post.getName()));
        List<Post> expected = getAllFromRepository();

        //act
        webTestClient.post()
                     .uri("/api/post/{id}/delete/", id)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody()
                     .returnResult();

        List<Post> actual = getAllFromRepository();

        expected.remove(removed);

        //assert
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        assertFalse(repository.existsById(id));
    }


    private List<Post> getAllFromRepository() {
        return repository.findAll();
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
