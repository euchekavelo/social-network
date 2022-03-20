package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostLike {
  int id;
  LocalDateTime time;
  int UserId;
  int postId;
}
