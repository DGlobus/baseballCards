package com.dasha.model;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Post {
    UUID id;
    String name;

}

