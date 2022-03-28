package ru.skillbox.socnetwork.model.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostComment {
    private int id;
    private LocalDateTime time;
    private int postId;
    private int parentId;
    private int authorId;
    private String commentText;
    private boolean isBlocked = false;
}
