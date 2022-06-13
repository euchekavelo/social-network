package ru.skillbox.socnetwork.controller;

import cn.apiclub.captcha.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.exception.InvalidRequestException;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.CaptchaDto;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.service.Captcha.CaptchaService;
import ru.skillbox.socnetwork.service.Captcha.CaptchaUtils;
import ru.skillbox.socnetwork.model.rsdto.NotificationSettingsDto;
import ru.skillbox.socnetwork.service.NotificationSettingsService;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
@InfoLogs
public class AccountController {

    private final PersonService personService;
    private final NotificationSettingsService notificationSettingsService;
    private final CaptchaService captchaService;

    @GetMapping("/register")
    public CaptchaDto captcha() {

        CaptchaDto captchaDto = new CaptchaDto();
        setupCaptcha(captchaDto);
        captchaService.addCaptcha(captchaDto);

        return captchaDto;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<GeneralResponse<DialogsResponse>> register(
            @RequestBody RegisterDto request) throws InvalidRequestException {

        Person person = personService.getPersonAfterRegistration(request);
        if (person == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralResponse<>("invalid_request", "string"));
        }

        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                person.getRegDate(),
                new DialogsResponse("ok")));
    }

    @PutMapping(value = "/password/recovery")
    public ResponseEntity<GeneralResponse<DialogsResponse>> recoverPassword(
            @RequestBody Map<String, String> body) throws InvalidRequestException {

        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                new DialogsResponse(personService.recoverPassword(body.get("email")))
        ));
    }

    @PutMapping(value = "/password/set")
    public ResponseEntity<GeneralResponse<DialogsResponse>> setPassword(
            @RequestBody Map<String, String> body) throws InvalidRequestException {

        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                new DialogsResponse(personService.setPassword(body))
        ));
    }

    @PutMapping(value = "/email/recovery")
    public ResponseEntity<GeneralResponse<DialogsResponse>> recoverEmail(
            @RequestBody Map<String, String> body) throws InvalidRequestException {

        return ResponseEntity.ok(
                new GeneralResponse<>(
                        "string",
                        System.currentTimeMillis(),
                        new DialogsResponse(personService.recoverEmail(body.get("email")))
                ));
    }

    @PutMapping(value = "/email")
    public ResponseEntity<GeneralResponse<DialogsResponse>> changeEmail(
            @RequestBody Map<String, String> body) throws InvalidRequestException {

        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                new DialogsResponse(personService.updateEmail(body))
        ));
    }

    @PutMapping(value = "/notifications")
    public ResponseEntity<GeneralResponse<Object>> changeNotificationSettings(
            @RequestBody Map<String, String> body) {

        String notificationType = body.get("notification_type");
        String enable = body.get("enable");

        System.out.println(notificationType + " " + enable);
        notificationSettingsService.changeSettingsToNotification(notificationType, enable);

        return ResponseEntity.ok(new GeneralResponse<Object>());

    }

    @GetMapping(value = "/notifications")
    public ResponseEntity<GeneralResponse<List<NotificationSettingsDto>>> getNotificationSettings() {

        List<NotificationSettingsDto> notificationSettings = notificationSettingsService.getSettingsForUser();
        GeneralResponse<List<NotificationSettingsDto>> response = new GeneralResponse<>(notificationSettings);
        return ResponseEntity.ok(response);

    }


    private void setupCaptcha(CaptchaDto captchaDto) {
        Captcha captcha = CaptchaUtils.createCaptcha(200, 50);
        captchaDto.setHidden(captcha.getAnswer());
        captchaDto.setImage(CaptchaUtils.encodeBase64(captcha));
    }
}
