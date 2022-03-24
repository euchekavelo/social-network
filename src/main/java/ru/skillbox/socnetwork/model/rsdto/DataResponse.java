
package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.Person;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor
public class DataResponse {
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
    private String photo;
    private String about;
    private String city;
    private String country;
    @JsonProperty("messages_permission")
    private String messagesPermission;
    @JsonProperty("last_online_time")
    private long lastOnlineTime;
    @JsonProperty("is_blocked")
    private boolean isBlocked;
    private String token;

    public DataResponse (Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
//        this.regDate = person.getRegDate().toEpochSecond(ZoneOffset.of("Europe/Berlin"));
//        this.birthDate = person.getBirthDate().toEpochDay();
        this.email = person.getEmail();
        this.phone = person.getPhone();
        this.photo = person.getPhoto();
        this.about = person.getAbout();
        this.city = person.getCity();
        this.country = person.getCountry();
//        this.messagesPermission = person.getMessagesPermission().toString();
//        this.lastOnlineTime = person.getLastOnlineTime().toEpochSecond(ZoneOffset.of("Europe/Berlin"));
        this.isBlocked = person.isBlocked();
    }
}
