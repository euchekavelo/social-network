package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.rqdto.NewPostDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.PersonService;
import ru.skillbox.socnetwork.service.PostService;

import java.util.List;


@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/")
public class ProfileController {

    private final PersonService personService;
    private final JwtTokenProvider tokenProvider;
    private final PostService postService;

    @GetMapping(path = "me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<PersonDto>> getMyProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        String email = securityUser.getUsername();
        PersonDto personDto = new PersonDto(personService.getByEmail(email));
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                personDto));
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<PersonDto>> getProfileById(@PathVariable int id) {
        PersonDto personDto = new PersonDto(personService.getById(id));
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                personDto));
    }

    @GetMapping(path = "{id}/wall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getWallByProfileId(@PathVariable int id,
                                                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                     @RequestParam(value = "itemPerPage", defaultValue = "20") int perPage) {
        GeneralResponse<List<PostDto>> response = new GeneralResponse<>(postService.getWall(id, offset, perPage));
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "{id}/wall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNewPost(@PathVariable int id,
                                             @RequestParam(value = "publish_date", defaultValue = "-1") long publishDate,
                                             @RequestBody NewPostDto newPostDto) {
        newPostDto.setAuthorId(id);
        if (publishDate == -1) {
            newPostDto.setTime(System.currentTimeMillis());
        } else {
            newPostDto.setTime(publishDate);
        }
        GeneralResponse<PostDto> response = new GeneralResponse<>(postService.addPost(newPostDto));
        return ResponseEntity.ok(response);
    }
}
