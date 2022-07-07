package com.dasha.controller.employee.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(description = "Контакты")
public class ContactsDto {
    @NotNull
    @ApiModelProperty(name = "Номер телефона")
    private String phone;
    @NotNull
    @ApiModelProperty(name= "Электронная почта")
    private String email;
    @ApiModelProperty(name = "Рабочая электронная почта")
    private String workEmail;
}
