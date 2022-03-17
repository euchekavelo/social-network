package ru.skillbox.social_network.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.social_network.model.rqdto.LoginDto;
import ru.skillbox.social_network.model.rsdto.ErrorResponse;
import ru.skillbox.social_network.model.rsdto.profile.CityResponse;
import ru.skillbox.social_network.model.rsdto.profile.CountryResponse;
import ru.skillbox.social_network.model.rsdto.profile.DataResponse;
import ru.skillbox.social_network.model.rsdto.profile.ProfileResponse;

@RequiredArgsConstructor
@Service
public class AuthService {

    public Object getLoginResponse(LoginDto request){

        if (request.getEmail().equals("petr@mail.ru") && request.getPassword().equals("111111")) {
            ProfileResponse response = new ProfileResponse();

            CityResponse cityResponse = new CityResponse();
            cityResponse.setId(1);
            cityResponse.setTitle("Москва");

            CountryResponse countryResponse = new CountryResponse();
            countryResponse.setId(1);
            countryResponse.setTitle("Россия");

            DataResponse dataResponse = new DataResponse();
            dataResponse.setId(1);
            dataResponse.setFirst_name("Петр");
            dataResponse.setLast_name("Петрович");
            dataResponse.setRegDate(1559751301818L);
            dataResponse.setBirthDate(1559751301818L);
            dataResponse.setEmail("petr@mail.ru");
            dataResponse.setPhoto("");
            dataResponse.setAbout("Родился в небольшой, но честной семье");
            dataResponse.setCity(cityResponse);
            dataResponse.setCountry(countryResponse);
            dataResponse.setMessagesPermission("ALL");
            dataResponse.setLastOnlineTime(1559751301818L);
            dataResponse.setBlocked(false);
            dataResponse.setToken("1q2e3e3r4t5");

            response.setError("string");
            response.setTimestamp(1559751301818L);
            response.setData(dataResponse);
            return response;
        }

        return new ErrorResponse();
    }
}
