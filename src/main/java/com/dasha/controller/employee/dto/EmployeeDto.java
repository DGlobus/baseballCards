package com.dasha.controller.employee.dto;

import com.dasha.model.JobType;
import com.dasha.model.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel(description = "Сотрудник")
public class EmployeeDto {
    @ApiModelProperty(name = "Имя")
    private String firstName;
    @ApiModelProperty(name = "Фамилия")
    private String lastName;
    @ApiModelProperty(name = "Описание")
    private String name;
    @ApiModelProperty(name = "Список характеристик")
    private List<String> characteristics;
    @ApiModelProperty(name = "Должность")
    private Post post;
    @ApiModelProperty(name = "Контактные данные")
    private ContactsDto contacts;
    @ApiModelProperty(name = "Тип занятости")
    private JobType jobType;
}
