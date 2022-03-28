
package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.Person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private String message;
    @JsonProperty("unread_count")
    private int unreadCount;
    @JsonProperty("last_message")
    private LastMessageResponse lastMessageResponse;

    public DataResponse(int count) {
        this.count = count;
    }

    private int count;

    public DataResponse(int id, int unreadCount, LastMessageResponse lastMessageResponse) {
        this.id = id;
        this.unreadCount = unreadCount;
        this.lastMessageResponse = lastMessageResponse;
    }

    public DataResponse(String message) {
        this.message = message;
    }

    public DataResponse(Person person, String token) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.regDate = getLong(person.getRegDate());
        this.birthDate = getLong(person.getBirthDate());
        this.email = person.getEmail();
        this.phone = person.getPhone();
        this.photo = person.getPhoto();
        this.about = person.getAbout();
        this.city = person.getCity();
        this.country = person.getCountry();
        this.messagesPermission = person.getMessagesPermission().toString();
        this.lastOnlineTime = getLong(person.getLastOnlineTime());
        this.isBlocked = person.isBlocked();
        this.token = token;
    }

    public DataResponse(Person person) {
        this(person, "");
    }

    private long getLong(LocalDateTime time) {
        return time.toEpochSecond(ZoneOffset.of("+03:00"));
    }

    private long getLong(LocalDate date) {
        return date.toEpochDay();
    }
}
