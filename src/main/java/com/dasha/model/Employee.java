package com.dasha.model;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Employee{
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private Post post;


    @Override
    public String toString() {
        return "First Name: " + firstName
                + "\nLast Name: " + lastName
                + "\nDescription: " + description
                + "\nCharacteristics: " + characteristics.stream().map(a->String.valueOf(a)).collect(Collectors.joining(", "))
                + "\nPost: " + post.name + "\n\n";
    }
}
