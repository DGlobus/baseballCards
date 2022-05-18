package com.dasha;

import java.util.List;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

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

    public void print(){
        try{
        PrintStream ps = new PrintStream(System.out, true, "UTF-8");

        ps.println("First Name: " + firstName);
        ps.println("Last Name: " + lastName);
        ps.println("Description: " + description);
        characteristics.stream().forEach(p -> ps.print(p + " "));
        ps.println();
        ps.println("Post: " + post.name);
        ps.println();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        
    }
}
