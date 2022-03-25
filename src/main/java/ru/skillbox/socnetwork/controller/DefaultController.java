package ru.skillbox.socnetwork.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class DefaultController {

    @GetMapping("/")
    public String mainPage() {
//        log.info("Test page opened!");
        return "index";
    }

    //    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = {"/shift-email", "/*/{path:^[^\\.]}"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "*")
    public String redirectToIndex() {
        return "forward:/";
    }

}
