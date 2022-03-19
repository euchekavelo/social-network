package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlockHistory {
  int id;
  LocalDateTime time;
  int personId;
  int postId;
  int commentId;
  TypeAction action;
}
