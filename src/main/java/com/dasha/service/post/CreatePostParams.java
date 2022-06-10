package com.dasha.service.post;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Builder
@Value
public class CreatePostParams {
    @NotBlank
    String name;
}
