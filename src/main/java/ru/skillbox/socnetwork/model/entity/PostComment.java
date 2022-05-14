package ru.skillbox.socnetwork.model.entity;


import lombok.Data;

@Data
public class PostComment {
    private Integer id;
    private Long time;
    private Integer postId;
    private Integer parentId;
    private Integer authorId;
    private String commentText;
    private Boolean isBlocked = false;
    private Boolean isLiked;
    private Integer likes;
}
