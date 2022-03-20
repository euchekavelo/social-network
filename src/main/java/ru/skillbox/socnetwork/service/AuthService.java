package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.ForDataResponse;
import ru.skillbox.socnetwork.model.rsdto.DataResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;

@RequiredArgsConstructor
@Service
public class AuthService {

    public ResponseEntity<GeneralResponse> getLoginResponse(LoginDto request) {
        if (request.getEmail().equals("petr@mail.ru")
                && request.getPassword().equals("11111111")) {
            return ResponseEntity.ok(new GeneralResponse(
                    "string",
                    1559751301818L,
                    new DataResponse(
                            1,
                            "Петр",
                            "Петрович",
                            1559751301818L,
                            1559751301818L,
                            "petr@mail.ru",
                            "89100000000",
                            "https://st2.depositphotos.com/1001599/7010/v/600/depositphotos_70104863-stock-illustration-man-holding-book-under-his.jpg",
                            "Родился в небольшой, но честной семье",
                            new ForDataResponse(
                                    1,
                                    "Москва"),
                            new ForDataResponse(
                                    1,
                                    "Россия"),
                            "ALL",
                            1559751301818L,
                            false,
                            "1q2e3e3r4t5"
                    )));
        }

        return ResponseEntity
                .badRequest()
                .body(new GeneralResponse(
                                "invalid_request",
                                "string"));
    }
}
