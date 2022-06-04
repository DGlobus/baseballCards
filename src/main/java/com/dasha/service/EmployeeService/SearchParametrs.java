package com.dasha.service.EmployeeService;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SearchParametrs {

    private String name;
    private UUID id;
}
