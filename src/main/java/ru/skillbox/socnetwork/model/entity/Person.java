package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

@Data
public class Person {

  private int id;
  private String firstName;
  private String lastName;
  private long regDate;
  private long birthDate;
  private String email;
  private String phone;
  private String password;
  private String photo;
  private String about;
  private String city;
  private String country;
  private String confirmationCode;
  private boolean isApproved = true;
  private TypePermission messagesPermission = TypePermission.ALL;
  private long lastOnlineTime;
  private boolean isBlocked = false;

}
