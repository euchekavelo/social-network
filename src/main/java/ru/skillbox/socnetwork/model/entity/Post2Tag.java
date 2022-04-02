package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

@Data
public class Post2Tag {
    private Integer id;
    private Integer tagId;
    private Integer postId;
}
