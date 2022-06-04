package com.dasha.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
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

    @Override
    public boolean equals(Object anotherEmployee){
        Employee emp = (Employee) anotherEmployee;
        if(emp != null){
            if(firstName.equals(emp.getFirstName()))
                if(lastName.equals(emp.getLastName()))
                    if(description.equals(emp.getDescription()))
                        if(characteristics.equals(characteristics))
                            if(post.getId().equals(emp.getPost().getId()))
                                return true;

        }
        return false;
    }
}
