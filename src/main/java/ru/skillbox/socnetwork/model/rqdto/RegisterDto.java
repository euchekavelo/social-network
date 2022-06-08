package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    @JsonProperty("passwd1")
    private String firstPassword;
    @JsonProperty("passwd2")
    private String secondPassword;
    private String firstName;
    private String lastName;
    @JsonProperty("captcha_id")
    private Long codeId;
    private String code;

    public boolean passwordsEqual() {
        return firstPassword.equals(secondPassword);
    }
}
