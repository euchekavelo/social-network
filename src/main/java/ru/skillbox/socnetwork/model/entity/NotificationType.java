package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

@Data
public class NotificationType {
  private int id;
  private TypeNotificationCode code;
  private String name;
}
