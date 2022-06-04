package com.dasha.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Post {
    UUID id;
    String name;

    public Post(UUID id, String name){
        this.id  =id;
        this.name = name;
    }
}

