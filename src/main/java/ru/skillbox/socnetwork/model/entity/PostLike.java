package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostLike {
    private Integer id;
    private Long time;
    private Integer personId;
    private Integer postId;
}
