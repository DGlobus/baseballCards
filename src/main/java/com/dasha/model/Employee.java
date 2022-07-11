package com.dasha.model;

import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class Employee{
    private UUID id;
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private Post post;
    private Contacts contacts;
    private JobType jobType;


    @Override
    public String toString() {
        return "First Name: " + firstName
                + "\nLast Name: " + lastName
                + "\nDescription: " + description
                + "\nCharacteristics: " + characteristics.stream().map(String::valueOf).collect(Collectors.joining(", "))
                + "\nPost: " + post.name
                + "\nContacts: "
                + "\n\temail: " + contacts.getEmail()
                + "\n\tphone: " + contacts.getPhone()
                + "\n\twork email: " + contacts.getWorkEmail()
                + "\nJob type: " + jobType + "\n\n";
    }
}
