package com.dasha.controller.post.mapper;

import com.dasha.controller.post.dto.PostDto;
import com.dasha.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDTO(Post post);
}
