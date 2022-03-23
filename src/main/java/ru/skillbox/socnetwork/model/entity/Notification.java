package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {
  private int id;
  private int typeId;
  private LocalDateTime sentTime;
  private int personId;
  private int notificationTypeId;
  private String contact;
}
