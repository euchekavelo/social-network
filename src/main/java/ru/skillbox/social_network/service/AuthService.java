
package ru.skillbox.social_network.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.social_network.model.rqdto.LoginDto;
import ru.skillbox.social_network.model.rsdto.GeneralResponse;
import java.util.Properties;

@RequiredArgsConstructor
@Service
public class AuthService {

    public ResponseEntity<GeneralResponse> getLoginResponse(LoginDto request) {
        if (request.getEmail().equals("petr@mail.ru")
                && request.getPassword().equals("111111")) {
            Properties properties = new Properties();
            Properties city = new Properties();
            Properties country = new Properties();
            city.put("id", 1);
            city.put("city", "Москва");
            country.put("id", 1);
            country.put("country", "Россия");
            properties.put("id", 1);
            properties.put("first_name", "Петр");
            properties.put("last_name", "Петрович");
            properties.put("reg_date", 1559751301818L);
            properties.put("birth_date", 1559751301818L);
            properties.put("email", "petr@mail.ru");
            properties.put("phone", "89100000000");
            properties.put("photo", "");
            properties.put("about", "Родился в небольшой, но честной семье");
            properties.put("city", city);
            properties.put("country", country);
            properties.put("messages_permission", "ALL");
            properties.put("last_online_time", 1559751301818L);
            properties.put("is_blocked", false);
            properties.put("token", "1q2e3e3r4t5");

            return ResponseEntity.ok(new GeneralResponse(
                    "string",
                    1559751301818L,
                    properties));
        }

        return ResponseEntity
                .badRequest()
                .body(new GeneralResponse(
                                "invalid_request",
                                "string"));
    }
}
