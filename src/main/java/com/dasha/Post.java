package com.dasha;

import java.util.UUID;

public class Post {
    UUID id;
    String name;

    public Post(UUID id, String name){
        this.id  =id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

