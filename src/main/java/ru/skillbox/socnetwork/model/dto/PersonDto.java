package ru.skillbox.socnetwork.model.dto;

import lombok.Data;
import ru.skillbox.socnetwork.model.entity.Person;

@Data
public class PersonDto {

    private int id;
    private String firstName;
    private String lastName;
    private Long regDate;
    private Long birthDate;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String messagesPermission;
    private Long lastOnlineTime;
    private boolean isBlocked;

    public PersonDto(Person person) {
        id = person.getId();
        firstName = person.getFirstName();
        lastName = person.getLastName();
        regDate = person.getRegDate();
        birthDate = person.getBirthDate();
        email = person.getEmail();
        phone = person.getPhone();
        photo = person.getPhoto();
        about = person.getAbout();
        city = person.getCity();
        country = person.getCountry();
        messagesPermission = person.getMessagesPermission().name();
        lastOnlineTime = person.getLastOnlineTime();
        isBlocked = person.isBlocked();
    }
}
