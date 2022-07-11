package com.dasha.service.employee.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SearchEmployeeParams {

    private String name;
    private UUID id;
}
