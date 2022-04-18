package ru.skillbox.socnetwork.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TempToken {
  private String email;
  private String token;
}
