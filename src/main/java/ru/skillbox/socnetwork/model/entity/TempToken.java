package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

@Data
public class TempToken {
  private Integer id;
  private String email;
  private String token;

  public TempToken(){}
  public TempToken(String email, String token){
    this.email = email;
    this.token = token;
  }
}
