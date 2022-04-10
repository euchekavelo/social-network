package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log
@RequiredArgsConstructor
@RestController
public class StorageController {

  @PostMapping(path = "/api/v1/storage/", produces = MediaType.APPLICATION_JSON_VALUE)
  public void uploadImage(){
    System.out.println("catching post!");
  }
}
