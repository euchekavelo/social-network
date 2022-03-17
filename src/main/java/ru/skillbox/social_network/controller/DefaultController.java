package ru.skillbox.social_network.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class DefaultController {

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("Welcome", "Test message");
        log.info("Test page opened!");
        return "index";
    }

}
