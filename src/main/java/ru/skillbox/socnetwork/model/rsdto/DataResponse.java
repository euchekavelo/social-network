
package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    private ForDataResponse city;
    private ForDataResponse country;
    @JsonProperty("messages_permission")
    private String messagesPermission;
    @JsonProperty("last_online_time")
    private long lastOnlineTime;
    @JsonProperty("is_blocked")
    private boolean isBlocked;
    private String token;
}
