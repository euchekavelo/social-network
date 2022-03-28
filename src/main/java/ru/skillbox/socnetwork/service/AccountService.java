//package ru.skillbox.socnetwork.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
//import ru.skillbox.socnetwork.model.rsdto.ForDataResponse;
//import ru.skillbox.socnetwork.model.rsdto.GeneralListResponse;
//
//import java.util.ArrayList;
//
//@RequiredArgsConstructor
//@Service
//public class AccountService {
//
//
//    public ResponseEntity<GeneralListResponse<ForDataResponse>> getRegisterResponse (RegisterDto request) {
//
//        ArrayList<ForDataResponse> dataArrayList = new ArrayList<>();
//        boolean errorsCheck = false;
//
//        if (!request.getFirstPassword().equals(request.getLastPassword())) {
//            dataArrayList.add(new ForDataResponse("passwords_do_not_match"));
//            errorsCheck = true;
//        }
//        if (request.getFirstPassword().length() < 8) {
//            dataArrayList.add(new ForDataResponse("password_is_too_short"));
//            errorsCheck = true;
//        }
//
//        //добавить еще проверки
//        //проверка почты, каптча
//
//        if (!errorsCheck) {
//            //отправить данные в БД
//            dataArrayList.add(new ForDataResponse("ok"));
//            return ResponseEntity.ok(new GeneralListResponse<ForDataResponse>(
//                    "string",
//                    1559751301818L,
//                    dataArrayList
//            ));
//        }
//
//        return ResponseEntity
//                .badRequest()
//                .body(new GeneralListResponse<ForDataResponse>(
//                "invalid_request",
//                "string"));
//    }
//}
