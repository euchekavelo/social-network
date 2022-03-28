package ru.skillbox.socnetwork.model.rqdto;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class LoginDto {
    private String email;
    private String password;

    /**
     *      TODO chek method checkPassword
     */
    public boolean checkPassword (String inDBPassword) {
        return new BCryptPasswordEncoder().matches(this.password, inDBPassword);
    }
}
