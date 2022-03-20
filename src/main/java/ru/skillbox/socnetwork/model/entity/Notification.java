package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {
  int id;
  int typeId;
  LocalDateTime sentTime;
  int personId;
  int notificationTypeId;
  String contact;
}
