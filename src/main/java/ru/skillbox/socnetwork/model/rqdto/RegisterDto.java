package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class RegisterDto {
    private String email;
    @JsonProperty("passwd1")
    private String firstPassword;
    @JsonProperty("passwd2")
    private String secondPassword;
    private String firstName;
    private String lastName;
    private String code;


    public boolean passwordsEqual() {
        return firstPassword.equals(secondPassword);
    }
}
