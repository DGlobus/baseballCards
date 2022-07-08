package com.dasha.controller.post;

import com.dasha.controller.post.dto.UpdatePostDto;
import com.dasha.controller.post.mapper.PostMapper;
import com.dasha.controller.post.dto.CreatePostDto;
import com.dasha.controller.post.dto.PostDto;
import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Post;
import com.dasha.service.post.CreatePostParams;
import com.dasha.service.post.PostService;
import com.dasha.service.post.UpdatePostParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
@Tag(name = "Контроллер должности")
public class PostController {

    private final PostService postService;
    private final PostMapper mapper;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation("Создать должность")
    public PostDto add(@RequestBody CreatePostDto params) {
        Post newPost = postService.create(CreatePostParams.builder()
                                                            .name(params.getName())
                                                            .build());
        return mapper.toDto(newPost);
    }

    @PostMapping("/{id}")
    @ApiOperation("Получить должность")
    public PostDto getById(@PathVariable UUID id){
        return mapper.toDto(postService.getById(id));
    }

    @PostMapping("/getAll")
    @ApiOperation("Получить все должности")
    public List<PostDto> getAll() {
        return mapper.toListDto(postService.getAll());
    }

    @PostMapping("/{id}/update")
    @ApiOperation("Обновить данные должности")
    public PostDto update(@PathVariable UUID id, @RequestBody UpdatePostDto params) throws ItemNotFoundException {
        Post updatedPost = postService.update(id, UpdatePostParams.builder()
                                                                    .name(params.getName())
                                                                    .build());
        return mapper.toDto(updatedPost);
    }

    @PostMapping("/{id}/delete")
    @ApiOperation("Удалить должность")
    public void delete(@PathVariable UUID id) {
        postService.delete(id);
    }

}
