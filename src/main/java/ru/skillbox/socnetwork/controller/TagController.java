package ru.skillbox.socnetwork.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;

@RestController
@RequestMapping("/api/v1/tags/")
public class TagController {

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getMyProfile() {
    return ResponseEntity.status(HttpStatus.OK).body(TempResponseDto.TAG_RESPONSE);
  }
}
