package com.dasha.controller.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@ApiModel("Модель должности")
public class PostDto {
    @NotNull
    private UUID id;
    @NotBlank
    @ApiModelProperty
    private String name;
}