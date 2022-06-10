package com.dasha.controller.post;

import com.dasha.controller.post.dto.CreatePostDto;
import com.dasha.controller.post.dto.PostDto;
import com.dasha.controller.post.mapper.PostMapper;
import com.dasha.model.Post;
import com.dasha.service.post.CreatePostParams;
import com.dasha.service.post.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper mapper;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostDto add(@RequestBody CreatePostDto params) {
        Post newPost = postService.create(CreatePostParams.builder()
                                                            .name(params.getName())
                                                            .build());
        return mapper.toDTO(newPost);
    }

    @PostMapping("/update/{id}")
    public PostDto update(@PathVariable UUID id, @RequestBody CreatePostDto params) {
        Post updatedPost = postService.update(id, CreatePostParams.builder()
                                                                    .name(params.getName())
                                                                    .build());
        return mapper.toDTO(updatedPost);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        postService.delete(id);
    }

    @PostMapping("/getById/{id}")
    public PostDto getById(@PathVariable UUID id) {
        return mapper.toDTO(postService.getById(id));
    }

    @PostMapping("/getAll")
    public List<PostDto> getAll() {
        return postService.getAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
