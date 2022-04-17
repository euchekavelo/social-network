package ru.skillbox.socnetwork.controller;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.filedto.FileUploadDTO;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.PersonService;
import ru.skillbox.socnetwork.service.StorageService;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

  private final PersonService personService;
  private final StorageService storageService;

  /* Transfer file to Storage service for upload file
  Service returns file metadata to build DTO, get Person from Context. These entities forms response.
  Next, delete current profile photo, set new file
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GeneralResponse<FileUploadDTO>> uploadImage(@RequestBody MultipartFile file) {
    Person person = null;
    FileMetadata fileMetadata = null;
    try {
      InputStream stream = new BufferedInputStream(file.getInputStream());
      String fileName = generateName(file.getOriginalFilename());
      fileMetadata = storageService.uploadFile(stream, fileName);
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
      person = personService.getByEmail(securityUser.getUsername());
      deleteFile(storageService.getRelativePath(person.getPhoto()));
      personService.updatePhoto(storageService.getAbsolutePath("/" + fileName), person);
    } catch (Exception e) {
      e.printStackTrace();
    }
    FileUploadDTO fileUploadDTO = new FileUploadDTO(person, fileMetadata);
    return ResponseEntity.ok(new GeneralResponse<>(
        "string",
        System.currentTimeMillis(),
        fileUploadDTO
    ));
  }

  @PostMapping(path = "/{token}")
  public void updateToken(@PathVariable String token){
    storageService.updateToken(token);
  }

  private void deleteFile(String path){
    try {
      storageService.deleteFile(path);
    } catch (DbxException e) {
      e.printStackTrace();
    }
  }

  private String generateName(String name){
    RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(65, 90).build();
    Pattern pattern = Pattern.compile(".*(\\.[A-z]*)$");
    Matcher matcher = pattern.matcher(name);
    String format = (matcher.find()) ?  matcher.group(1) : "";
    return generator.generate(10) + format;
  }
}
