package com.dasha.action;

import com.dasha.model.Employee;
import com.dasha.model.PostMap;
import com.dasha.service.getCards.EmployeeDTO;

import java.util.UUID;

public class FromDtoToEmployee {

    private static PostMap map = new PostMap();

    public static Employee parse(EmployeeDTO dto){
        return Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .description(dto.getDescription())
                .characteristics(dto.getCharacteristics())
                .post(map.getPosts().get(fromStringToUuid(dto.getPostId())))
                .build();

    }

    private static UUID fromStringToUuid(String postId){
        return UUID.fromString(postId);
    }

}
