package com.dasha.controller.employee.dto;

import com.dasha.model.JobType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@ApiModel(description = "Объект обноваления данных сотрудника")
public class UpdateEmployeeDto {
    @NotBlank
    @ApiModelProperty(name = "Имя", required = true)
    private String firstName;
    @NotBlank
    @ApiModelProperty(name = "Фамилия", required = true)
    private String lastName;
    @ApiModelProperty(name = "Описание")
    private String description;
    @NotNull
    @ApiModelProperty(name = "Список характеристик", required = true)
    private List<String> characteristics;
    @NotNull
    @ApiModelProperty(name = "Идентификатор должности", required = true)
    private UUID postId;
    @NotNull
    @ApiModelProperty(name = "Контактные данные", required = true)
    private ContactsDto contacts;
    @NotNull
    @ApiModelProperty(name = "Тип занятости", required = true)
    private JobType jobType;
}
