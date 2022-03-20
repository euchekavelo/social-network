package ru.skillbox.socnetwork.model.entity;


import lombok.Data;

@Data
public class NotificationType {
  int id;
  TypeNotificationCode code;
  String name;
}
