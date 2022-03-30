package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.rsdto.PersonResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.FeedsService;
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
    public ResponseEntity<GeneralResponse<PersonResponse>> getMyProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        String email = securityUser.getUsername();
        PersonResponse personResponse = new PersonResponse(personService.getByEmail(email));
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                personResponse));
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<PersonResponse>> getProfileById(@PathVariable int id) {
        PersonResponse personResponse = new PersonResponse(personService.getById(id));
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                personResponse));
    }

    @GetMapping(path = "{id}/wall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getWallByProfileId(@PathVariable int id,
                                                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                     @RequestParam(value = "perPage", defaultValue = "20") int perPage) {
        GeneralResponse<List<PostDto>> response = new GeneralResponse<>(postService.getWall(id, offset, perPage));
        return ResponseEntity.ok(response);
    }
}
