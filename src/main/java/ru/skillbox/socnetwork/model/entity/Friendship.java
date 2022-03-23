package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

@Data
public class Friendship {
  private int id;
  private int statusId;
  private int srcPersonId;
  private int dstPersonId;
}
