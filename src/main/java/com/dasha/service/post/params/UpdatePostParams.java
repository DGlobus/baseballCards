package com.dasha.service.post.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@Value
public class UpdatePostParams {
    @NotBlank
    String name;
}
