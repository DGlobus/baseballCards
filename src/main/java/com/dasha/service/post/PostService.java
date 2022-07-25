package com.dasha.service.post;

import com.dasha.model.Post;
import com.dasha.repository.PostRepository;
import com.dasha.service.post.params.CreatePostParams;
import com.dasha.service.post.params.UpdatePostParams;
import com.dasha.util.Guard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    @Transactional
    public Post create(CreatePostParams createPostParams) {
        Post post = createPost(createPostParams);
        repository.save(post);
        return post;
    }

    @Transactional
    public Post getById(@NotNull UUID postId) {
        Guard.checkAndThrowItemNotFoundException(repository.existsById(postId), "Данной должности не существует " + postId);
        return repository.findById(postId).orElse(null);
    }

    @Transactional
    public List<Post> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Transactional
    public Post update(@NotNull UUID postId, UpdatePostParams params) {
        Post post = getById(postId);
        post.setName(params.getName());
        repository.save(post);
        return post;
    }

    @Transactional
    public void delete(@NotNull UUID postId) {
        repository.deleteById(postId);
    }

    private Post createPost(CreatePostParams createPostParams) {
        return Post.builder()
                   .id(UUID.randomUUID())
                   .name(createPostParams.getName())
                   .build();
    }

    @PostConstruct
    private void fill() {
        repository.save(new Post(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"), "student"));
        repository.save(new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "middle developer"));
        repository.save(new Post(UUID.fromString("399878e4-84fc-452b-968f-4209ae4c19a8"), "random person"));
    }
}

