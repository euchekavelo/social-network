package ru.skillbox.socnetwork.model.entity;


import lombok.Data;

@Data
public class PostFile {
    private Integer id;
    private Integer postId;
    private String name;
    private String path;
}
