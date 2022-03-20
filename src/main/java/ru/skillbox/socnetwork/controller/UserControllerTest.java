package ru.skillbox.socnetwork.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.entity.User;
import ru.skillbox.socnetwork.service.UserService;

import java.util.List;

@RestController
public class UserControllerTest {
    private UserService service;

    public UserControllerTest(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getAll() {
        return this.service.getAll();
    }
}
