package com.dasha.service;

import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Post;
import com.dasha.service.post.CreatePostParams;
import com.dasha.service.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostServiceTest {

    private PostService postService;

    List<Post> posts;

    @BeforeEach
    void setup(){
        posts = List.of(
                new Post(UUID.fromString("399878e4-84fc-452b-968f-4209ae4c19a8"), "random person"),
                new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student"),
                new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer")
        );
        postService = new PostService();
        try{
            Method method = postService.getClass().getDeclaredMethod("fill");
            method.setAccessible(true);
            method.invoke(postService);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Test
    void create(){
        //arrange
        Post expected = new Post(UUID.randomUUID(), "Manager");

        //act
        Post actual = postService.create(new CreatePostParams("Manager"));

        //assert
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void getById(){
        //arrange
        Post expected = posts.get(0);

        //act
        Post actual = postService.getById(expected.getId());

        //assert
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void getIdNotExist(){
        UUID id = UUID.randomUUID();
        ItemNotFoundException ex = assertThrows(ItemNotFoundException.class, () -> {postService.getById(id);});

        String expected = "Данной должности не существует " + id;
        String actual = ex.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void getAll(){
        //arrange
        List<Post> expected = posts;

        //act
        List<Post> actual = postService.getAll();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    void update(){
        //arrange
        Post expected = posts.get(0);
        expected.setName("Manager");
        CreatePostParams params = new CreatePostParams("Manager");

        //act
        Post actual = postService.update(expected.getId(), params);

        //assert
        assertEquals(actual, expected);
    }

    @Test
    void delete(){
        //arrange
        UUID id = posts.get(0).getId();

        //act
        postService.delete(id);

        //assert
        assertThrows(ItemNotFoundException.class, () -> {postService.getById(id);});
    }
}
