package com.dasha;

import java.util.List;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

public class Employee{
    String firstName;
    String lastName;
    String description;
    List<String> characteristics;
    Post post;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCharacteristics(List<String> characteristics) {
        this.characteristics = characteristics;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCharacteristics() {
        return characteristics;
    }

    public Post getPost() {
        return post;
    }

    @Override
    public String toString() {
        String b = "";
        return "First Name: " + firstName
                + "\nLast Name: " + lastName
                + "\nDescription: " + description
                + "\n" + characteristics.stream().map(a->String.valueOf(a)).collect(Collectors.joining(", "))
                + "\nPost: " + post.name + "\n\n";
    }

}
