package com.dasha.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Employee {
    @Id
    @NotNull
    private UUID id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    private List<String> characteristics;
    @ManyToOne
    @JoinColumn
    @NotNull
    private Post post;
    @Embedded
    @NotNull
    private Contacts contacts;
    @NotNull
    private JobType jobType;

    @Override
    public String toString() {
        return "First Name: " + firstName
               + "\nLast Name: " + lastName
               + "\nDescription: " + description
               + "\nCharacteristics: " + characteristics.stream().map(String::valueOf).collect(Collectors.joining(", "))
               + "\nPost: " + post.name
               + "\nContacts: "
               + "\n" + contacts.toString()
               + "\nJob type: " + jobType + "\n\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.getId())
               && firstName.equals(employee.getFirstName())
               && lastName.equals(employee.getLastName())
               && (description != null ? description.equals(employee.getDescription()) : employee.getDescription() == null)
               && characteristics.equals(employee.getCharacteristics())
               && post.equals(employee.getPost())
               && contacts.equals(employee.getContacts())
               && jobType.equals(employee.getJobType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post);
    }
}
