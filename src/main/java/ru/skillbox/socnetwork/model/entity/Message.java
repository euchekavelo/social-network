package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
  int id;
  LocalDateTime time;
  int authorId;
  int recipientId;
  String messageText;
  TypeReadStatus readStatus;
}
