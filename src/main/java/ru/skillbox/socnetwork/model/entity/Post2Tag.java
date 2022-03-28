package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

@Data
public class Post2Tag {
    private int id;
    private int tagId;
    private int postId;
}
