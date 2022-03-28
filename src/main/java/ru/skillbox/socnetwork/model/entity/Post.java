package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private int id;
    private LocalDateTime time;
    private int authorId;
    private String title;
    private String postText;
    private boolean isBlocked = false;
}
