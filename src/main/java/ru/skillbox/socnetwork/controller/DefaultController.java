package ru.skillbox.socnetwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.skillbox.socnetwork.logging.InfoLogs;

@Controller
@InfoLogs
public class DefaultController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "*")
    public String redirectToIndex() {
        return "forward:/";
    }

}
