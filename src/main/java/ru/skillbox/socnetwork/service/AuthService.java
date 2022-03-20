package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.ProfileDto;

import java.util.Hashtable;

@RequiredArgsConstructor
@Service
public class AuthService {

    public ProfileDto getLoginResponse(LoginDto request){
        ProfileDto response = new ProfileDto();

        if (request.getEmail().equals("petr@mail.ru") && request.getPassword().equals("111111")) {
            Hashtable<String, Object> cityHash = new Hashtable<>();
            Hashtable<String, Object> countryHash = new Hashtable<>();
            Hashtable<String, Object> dataHash = new Hashtable<>();
            cityHash.put("id", 1);
            cityHash.put("title", "Москва");
            countryHash.put("id", 1);
            countryHash.put("title", "Россия");
            dataHash.put("id", 1);
            dataHash.put("first_name", "Петр");
            dataHash.put("last_name", "Петрович");
            dataHash.put("reg_date", 1559751301818L);
            dataHash.put("birth_date", 1559751301818L);
            dataHash.put("email", "petr@mail.ru");
            dataHash.put("phone", "89100000000");
            dataHash.put("photo", "");
            dataHash.put("about", "Родился в небольшой, но честной семье");
            dataHash.put("city", cityHash);
            dataHash.put("country", countryHash);
            dataHash.put("messages_permission", "ALL");
            dataHash.put("last_online_time", 1559751301818L);
            dataHash.put("is_blocked", false);
            dataHash.put("token", "1q2e3e3r4t5");
            response.setError("string");
            response.setTimestamp(1559751301818L);
            response.setData(dataHash);

            return response;
        }

        response.setError("invalid_request");
        response.setErrorDescription("string");

        return response;
    }
}
