package com.dasha.service;

import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Post;
import com.dasha.repository.PostRepository;
import com.dasha.service.post.PostService;
import com.dasha.service.post.params.CreatePostParams;
import com.dasha.service.post.params.UpdatePostParams;
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
public class PostServiceTest {
    @Autowired
    private PostRepository repository;

    private PostService postService;


    @BeforeEach
    void setup() {

        repository.save(new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student"));
        repository.save(new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer"));
        repository.save(new Post(UUID.fromString("399878e4-84fc-452b-968f-4209ae4c19a8"), "random person"));
        postService = new PostService(repository);
    }

    @AfterEach
    void clean() {
        repository.deleteAll();
    }


    @Test
    void create() {
        //arrange
        Post expected = new Post(UUID.randomUUID(), "Manager");

        //act
        Post actual = postService.create(new CreatePostParams("Manager"));

        //assert
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
        assertTrue(repository.existsById(actual.getId()));
    }

    @Test
    void getById() {
        //arrange
        Post expected = repository.getOne(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"));

        //act
        Post actual = postService.getById(expected.getId());

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getIdNotExist() {
        UUID id = UUID.randomUUID();
        ItemNotFoundException ex = assertThrows(ItemNotFoundException.class, () -> {postService.getById(id);});

        String expected = "Данной должности не существует " + id;
        String actual = ex.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        //arrange
        List<Post> expected = repository.findAll();

        //act
        List<Post> actual = postService.getAll();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        //arrange
        Post expected = repository.getOne(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"));
        expected.setName("Manager");
        UpdatePostParams params = new UpdatePostParams("Manager");

        //act
        postService.update(expected.getId(), params);
        Post actual = repository.getOne(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"));

        //assert
        assertEquals(actual, expected);
    }

    @Test
    void delete() {
        //arrange
        UUID id = UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5");

        //act
        postService.delete(id);

        //assert
        assertThrows(ItemNotFoundException.class, () -> {postService.getById(id);});
        assertFalse(repository.existsById(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")));
    }
}
