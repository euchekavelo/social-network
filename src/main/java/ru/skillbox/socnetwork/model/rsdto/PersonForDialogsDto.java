package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonForDialogsDto {
    private Integer id;
    private String photo;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("e_mail")
    private String eMail;
    @JsonProperty("last_online_time")
    private Long lastOnlineTime;

    public PersonForDialogsDto() {

    }
}
