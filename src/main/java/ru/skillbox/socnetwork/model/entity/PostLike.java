package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostLike {
    private int id;
    private long time;
    private int personId;
    private int postId;
}
