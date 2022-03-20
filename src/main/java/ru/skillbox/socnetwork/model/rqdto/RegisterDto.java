package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    @JsonProperty("passwd1")
    private String firstPassword;
    @JsonProperty("passwd2")
    private String lastPassword;
    private String firstName;
    private String lastName;
    private String code;
}
