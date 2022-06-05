package com.dasha.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class Post {
    UUID id;
    String name;

    public Post(UUID id, String name){
        this.id  =id;
        this.name = name;
    }
}

