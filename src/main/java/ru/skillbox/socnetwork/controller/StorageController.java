package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.filedto.FileUploadDTO;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.PersonService;
import ru.skillbox.socnetwork.service.PhotoStorageService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

  private final PersonService personService;
  private final PhotoStorageService photoStorageService;

  @Value("${skillbox.app.tempFolderPath}")
  private String path;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GeneralResponse<FileUploadDTO>> uploadImage(@RequestBody MultipartFile file) {
    File photo = new File(path + file.getName());
    try {
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(photo));
        stream.write(file.getBytes());
        stream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }


      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
      Person person = personService.getByEmail(securityUser.getUsername());
      return ResponseEntity.ok(new GeneralResponse<>(
              "string",
              System.currentTimeMillis(),
              new FileUploadDTO(person, photo)
      ));
  }
}
