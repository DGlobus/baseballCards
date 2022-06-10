package com.dasha.service.post;

import com.dasha.exceptions.exception.ItemNotFoundException;
import com.dasha.model.Post;
import com.dasha.service.ModelService;
import com.dasha.util.Guard;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class PostService implements ModelService<Post, CreatePostParams> {
    private final Map<UUID, Post> posts = new HashMap<>();

    public PostService(){
        fill(); //for test
    }

    @Override
    public Post create(CreatePostParams createPostParams) {
        Post post = createPost(createPostParams);
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public Post update(@NonNull UUID postId, CreatePostParams params) {
        if(!posts.containsKey(postId)){
            throw new ItemNotFoundException("Данной должности не существует");
        }
        Post post = posts.get(postId);
        post.setName(params.getName());
        posts.replace(postId, post);
        return post;
    }

    @Override
    public void delete(@NonNull UUID postId) {
        posts.remove(postId);
    }

    @Override
    public Post getById(@NonNull UUID postId) {
        if(!posts.containsKey(postId)){
            throw new ItemNotFoundException("Данной должности не существует");
        }
        return posts.get(postId);
    }

    @Override
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
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer"));
        posts.put(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student"));
        posts.put(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707"), "random person"));
    }
}
