package com.dasha.controller.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@ApiModel(description = "Должность")
public class PostDto {
    @NotNull
    @ApiModelProperty(name = "Идентификатор должности")
    private UUID id;
    @NotNull
    @ApiModelProperty(name = "Название должности")
    private String name;
}
