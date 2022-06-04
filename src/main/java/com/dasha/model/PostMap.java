package com.dasha.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PostMap {

    private Map<UUID, Post> posts = new HashMap<>();


    public PostMap(){
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer"));
        posts.put(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student"));
        posts.put(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707"), "random person"));

    }
}
