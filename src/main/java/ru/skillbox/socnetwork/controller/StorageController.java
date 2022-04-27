package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.filedto.FileUploadDTO;
import ru.skillbox.socnetwork.service.storage.StorageService;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

  private final StorageService storageService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GeneralResponse<FileUploadDTO>> uploadImage(@RequestBody MultipartFile file) {

    return ResponseEntity.ok(new GeneralResponse<>(
        "string",
        System.currentTimeMillis(),
        storageService.uploadFile(file)
    ));
  }

}
