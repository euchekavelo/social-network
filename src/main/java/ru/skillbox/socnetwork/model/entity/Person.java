package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Person {
  private int id;
  private String firstName;
  private String lastName;
  private LocalDateTime timestamp;
  private LocalDate birthDate;
  private String email;
  private String phone;
  private String password;
  private String photo;
  private String about;
  private String town;
  private String confirmationCode;
  private boolean isApproved = true;
  private TypePermission messagesPermission = TypePermission.ALL;
  private LocalDateTime lastOnlineTime;
  private boolean isBlocked = false;
}
