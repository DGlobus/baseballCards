package com.dasha.controller.post.mapper;

import com.dasha.controller.post.dto.CreatePostDto;
import com.dasha.controller.post.dto.PostDto;
import com.dasha.model.Post;
import com.dasha.service.employee.params.CreateEmployeeParams;
import com.dasha.service.post.CreatePostParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDto(Post post);

    CreatePostParams toParams(CreatePostDto dto);

    PostDto toDto(CreatePostDto dto);

    List<PostDto> toDto(List<Post> posts);
}
