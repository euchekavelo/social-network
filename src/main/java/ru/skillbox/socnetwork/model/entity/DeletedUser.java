package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

@Data
public class DeletedUser {
  private Integer id;
  private Integer personId;
  private String photo;
  private String firstName;
  private String lastName;
  private Long expire;

  public DeletedUser(){}
  public DeletedUser(Person person) {
    this.personId = person.getId();
    this.photo = person.getPhoto();
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
    expire = System.currentTimeMillis() + 259200000;
  }
}