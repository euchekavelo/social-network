package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Person {
  private int id;
  private String firstName;
  private String lastName;
  private LocalDateTime regDate;
  private LocalDate birthDate;
  private String email;
  private String phone;
  private String password;
  private String photo = "https://st2.depositphotos.com/1001599/7010/v/600/depositphotos_70104863-stock-illustration-man-holding-book-under-his.jpg";
  private String about;
  private String city;
  private String country;
  private String confirmationCode;
  private boolean isApproved = true;
  private TypePermission messagesPermission = TypePermission.ALL;
  private LocalDateTime lastOnlineTime;
  private boolean isBlocked = false;
}
