package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
  int id;
  LocalDateTime time;
  int authorId;
  String title;
  String postText;
  boolean isBlocked = false;
}
