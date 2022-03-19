package ru.skillbox.socnetwork.model.entity;


import lombok.Data;

@Data
public class PostFile {
  int id;
  int postId;
  String name;
  String path;
}
