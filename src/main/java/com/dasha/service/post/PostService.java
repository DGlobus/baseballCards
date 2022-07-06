package com.dasha.service.post;

import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Post;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class PostService {
    private final Map<UUID, Post> posts = new HashMap<>();

    public PostService(){
        fill(); //for test
    }

    public Post create(CreatePostParams createPostParams) {
        Post post = createPost(createPostParams);
        posts.put(post.getId(), post);
        return post;
    }

    public Post update(@NotNull UUID postId, CreatePostParams params) {
        Post post = getById(postId);
        post.setName(params.getName());
        posts.replace(postId, post);
        return post;
    }

    public void delete(@NotNull UUID postId) {
        posts.remove(postId);
    }

    public Post getById(@NotNull UUID postId) {
        if(!posts.containsKey(postId)){
            throw new ItemNotFoundException("Данной должности не существует " + postId);
        }
        return posts.get(postId);
    }

    public List<Post> getAll() {
        return new ArrayList<>(posts.values());
    }

    private Post createPost(CreatePostParams createPostParams) {
        return Post.builder()
                    .id(UUID.randomUUID())
                    .name(createPostParams.getName())
                    .build();
    }

    @PostConstruct
    private void fill(){
        posts.put(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student"));
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer"));
        posts.put(UUID.fromString("399878e4-84fc-452b-968f-4209ae4c19a8"), new Post(UUID.fromString("399878e4-84fc-452b-968f-4209ae4c19a8"), "random person"));
    }
}

