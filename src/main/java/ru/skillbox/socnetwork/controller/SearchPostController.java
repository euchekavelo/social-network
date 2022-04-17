package ru.skillbox.socnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.rsdto.GeneralListResponse;
import ru.skillbox.socnetwork.model.rsdto.MessageResponseDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;

@RestController
@RequestMapping("api/v1/users/search")
public class SearchPostController {

    // http://localhost:8086/api/v1/users/search?first_name=text 400

//    @GetMapping()
//    public void  getListFriendRequests(@PathVariable String text) { ///request param
////        GeneralListResponse<MessageResponseDto> generalListResponse =
////                new GeneralListResponse<>(friendsService.getListIncomingFriendRequests(), offset, itemPerPage);
////
////        return ResponseEntity.ok(generalListResponse);
//        System.out.println(text);
//    }
}
