package com.dasha.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @NotNull
    UUID id;
    @NotNull
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.getId())
               && name.equals(post.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

