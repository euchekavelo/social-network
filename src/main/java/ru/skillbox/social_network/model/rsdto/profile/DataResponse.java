package ru.skillbox.social_network.model.rsdto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataResponse {

    private int id;
    private String first_name;
    private String last_name;
    @JsonProperty("reg_date")
    private long regDate;
    @JsonProperty("birth_date")
    private long birthDate;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private CityResponse city;
    private CountryResponse country;
    @JsonProperty("messages_permission")
    private String messagesPermission;
    @JsonProperty("last_online_time")
    private long lastOnlineTime;
    @JsonProperty("is_blocked")
    private boolean isBlocked;
    private String token;
}
