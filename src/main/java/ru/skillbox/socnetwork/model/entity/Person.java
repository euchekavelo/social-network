package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import ru.skillbox.socnetwork.model.entity.enums.TypePermission;

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
    private String photo;
    private String about;
    private String city;
    private String country;
    private String confirmationCode;
    private boolean isApproved;
    private TypePermission messagesPermission;
    private LocalDateTime lastOnlineTime;
    private boolean isBlocked;

    public Person() {
        this.photo = "https://st2.depositphotos.com/1001599/7010/v/600/depositphotos_70104863-stock-illustration-man-holding-book-under-his.jpg";
        this.messagesPermission = TypePermission.ALL;
        this.isApproved = true;
        this.isBlocked = false;
    }
}
