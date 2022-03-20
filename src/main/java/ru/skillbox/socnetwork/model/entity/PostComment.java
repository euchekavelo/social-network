package ru.skillbox.socnetwork.model.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostComment {
  int id;
  LocalDateTime time;
  int postId;
  int parentId;
  int authorId;
  String commentText;
  boolean isBlocked = false;
}
