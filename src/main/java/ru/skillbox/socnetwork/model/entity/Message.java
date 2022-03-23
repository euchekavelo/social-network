package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
  private int id;
  private LocalDateTime time;
  private int authorId;
  private int recipientId;
  private String messageText;
  private TypeReadStatus readStatus;
}
