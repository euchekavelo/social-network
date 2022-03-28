//package ru.skillbox.socnetwork.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import ru.skillbox.socnetwork.model.rqdto.LoginDto;
//import ru.skillbox.socnetwork.model.rsdto.ForDataResponse;
//import ru.skillbox.socnetwork.model.rsdto.PersonResponse;
//import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
//import ru.skillbox.socnetwork.repository.PersonRepository;
//import ru.skillbox.socnetwork.security.JwtTokenProvider;
//
//import javax.servlet.ServletRequest;
//
//@RequiredArgsConstructor
//@Service
//public class AuthService {
//
//    private final JwtTokenProvider tokenProvider;
//    private final ServletRequest servletRequest;
//    private final PersonRepository personRepository;
//
//    public ResponseEntity<GeneralResponse<PersonResponse>> getLoginResponse(LoginDto request) {
//        if (request.getEmail().equals("test@mail.ru")
//                && request.getPassword().equals("11111111")) {
//            System.out.println("!!!");
//            //System.out.println(personRepository.isEmptyEmail(request.getEmail()));
//            return ResponseEntity.ok(new GeneralResponse<PersonResponse>(
//                    "string",
//                    1559751301818L,
//                    new PersonResponse(
//                            1,
//                            "Петр",
//                            "Петрович",
//                            1559751301818L,
//                            1559751301818L,
//                            "mail@mail.ru",
//                            "89100000000",
//                            "https://st2.depositphotos.com/1001599/7010/v/600/depositphotos_70104863-stock-illustration-man-holding-book-under-his.jpg",
//                            "Родился в небольшой, но честной семье",
//                            new ForDataResponse(
//                                    1,
//                                    "Москва"),
//                            new ForDataResponse(
//                                    1,
//                                    "Россия"),
//                            "ALL",
//                            1559751301818L,
//                            false,
//                            tokenProvider.generateToken(request.getEmail())
//                    )));
//        }
//
//        return ResponseEntity
//                .badRequest()
//                .body(new GeneralResponse<>(
//                                "invalid_request",
//                                "string"));
//    }
//}
