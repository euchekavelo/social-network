package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Person {
  int id;
  String firstName;
  String lastName;
  LocalDateTime timestamp;
  LocalDate birthDate;
  String email;
  String phone;
  String password;
  String photo;
  String about;
  String town;
  String confirmationCode;
  boolean isApproved = true;
  TypePermission messagesPermission = TypePermission.ALL;
  LocalDateTime lastOnlineTime;
  boolean isBlocked = false;
}
