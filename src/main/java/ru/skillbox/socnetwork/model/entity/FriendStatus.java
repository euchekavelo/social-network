package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendStatus {

  int id;
  LocalDateTime time;
  String name;
  TypeCode code;
}
