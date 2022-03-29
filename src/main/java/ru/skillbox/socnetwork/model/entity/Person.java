package ru.skillbox.socnetwork.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Person {

  private int id;
  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("last_name")
  private String lastName;
  @JsonProperty("reg_date")
  private long regDate;
  @JsonProperty("birth_date")
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
  @JsonProperty("is_blocked")
  private boolean isBlocked = false;

}
