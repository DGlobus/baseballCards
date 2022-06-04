package com.dasha.service.getCards;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private String postId;
}
