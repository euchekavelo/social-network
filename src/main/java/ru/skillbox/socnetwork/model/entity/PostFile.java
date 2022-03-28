package ru.skillbox.socnetwork.model.entity;


import lombok.Data;

@Data
public class PostFile {
    private int id;
    private int postId;
    private String name;
    private String path;
}
