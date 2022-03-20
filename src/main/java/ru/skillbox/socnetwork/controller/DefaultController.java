
package ru.skillbox.socnetwork.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class DefaultController {

    @GetMapping("/")
    public String mainPage() {
        log.info("Test page opened!");
        return "index";
    }

}
