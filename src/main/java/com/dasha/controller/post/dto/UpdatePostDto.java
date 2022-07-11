package com.dasha.controller.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Объект обновления должности")
public class UpdatePostDto {
    @NotBlank
    @ApiModelProperty(name = "Название должности")
    private String name;
}
