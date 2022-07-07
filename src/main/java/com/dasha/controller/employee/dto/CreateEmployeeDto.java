package com.dasha.controller.employee.dto;

import com.dasha.model.JobType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Объект создания пользователя")
public class CreateEmployeeDto {
    @NotBlank
    @ApiModelProperty(name = "Имя")
    private String firstName;
    @NotBlank
    @ApiModelProperty(name = "Фамилия")
    private String lastName;
    @ApiModelProperty(name = "Описание")
    private String name;
    @NotNull
    @ApiModelProperty(name = "Список характеристик")
    private List<String> characteristics;
    @NotNull
    @ApiModelProperty(name = "Идентификатор должности")
    private UUID postId;
    @NotNull
    @ApiModelProperty(name = "Контактные данные")
    private ContactsDto contacts;
    @NotNull
    @ApiModelProperty(name = "Тип занятости")
    private JobType jobType;
}

