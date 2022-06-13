package ru.skillbox.socnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import cn.apiclub.captcha.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.exception.ErrorResponseDto;
import ru.skillbox.socnetwork.exception.InvalidRequestException;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.CaptchaDto;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.service.Captcha.CaptchaService;
import ru.skillbox.socnetwork.service.Captcha.CaptchaUtils;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
@InfoLogs
@Tag(name="account", description="Взаимодействие с аккаунтом")
public class AccountController {

    private final PersonService personService;
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

    @Operation(summary = "Регистрация пользователя",
        responses = {
            @ApiResponse(responseCode = "400", description = "Bad request",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                ))),
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = GeneralResponse.class)
                )))
        })
    public ResponseEntity<GeneralResponse<DialogsResponse>> register(@RequestBody RegisterDto request) {
        Person person = personService.getPersonAfterRegistration(request);

        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            person.getRegDate(),
            new DialogsResponse("ok")));
    }

    @PutMapping(value = "/password/recovery")
    public ResponseEntity<GeneralResponse<DialogsResponse>> recoverPassword(
            @RequestBody Map<String, String> body) throws InvalidRequestException {
    @Operation(summary = "Восстановление пароля", description = "Отправляет на email ссылку для восстановления пароля",
        parameters = @Parameter(name = "email", example = "some@mail.ru"),
        responses = {
            @ApiResponse(responseCode = "400", description = "Bad request",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "200", description = "Ссылка на восстановление пароля отправлена на email",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = GeneralResponse.class)
                    )))
        })
    public ResponseEntity<GeneralResponse<DialogsResponse>> recoverPassword(@RequestBody Map<String, String> body) throws InvalidRequestException { //TODO: создать DTO

        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            new DialogsResponse(personService.recoverPassword(body.get("email")))
        ));
    }

    @PutMapping(value = "/password/set")
    public ResponseEntity<GeneralResponse<DialogsResponse>> setPassword(
            @RequestBody Map<String, String> body) throws InvalidRequestException {
    @Operation(summary = "Изменение пароля",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                schemaProperties = {}
            )),
        responses = {
            @ApiResponse(responseCode = "400", description = "Bad request",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "200", description = "Успешное изменение пароля",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = GeneralResponse.class)
                    )))
        })
    public ResponseEntity<GeneralResponse<DialogsResponse>> setPassword(@RequestBody Map<String, String> body) throws InvalidRequestException {//TODO: создать DTO

        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            new DialogsResponse(personService.setPassword(body))
        ));
    }

    @PutMapping(value = "/email/recovery")
    @Operation(summary = "Восстановление email", description = "Отправляет на email ссылку для восстановления",
        parameters = @Parameter(name = "email", example = "some@mail.ru"),
        responses = {
            @ApiResponse(responseCode = "400", description = "Bad request",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "200", description = "Ссылка на восстановление отправлена на email",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = GeneralResponse.class)
                    )))
        })
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
    @Operation(summary = "Изменение email",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                schemaProperties = {}
            )),
        responses = {
            @ApiResponse(responseCode = "400", description = "Bad request",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                    ))),
            @ApiResponse(responseCode = "200", description = "Ссылка на восстановление пароля отправлена на email",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = GeneralResponse.class)
                    )))
        })
    public ResponseEntity<GeneralResponse<DialogsResponse>> changeEmail(
        @RequestBody Map<String, String> body) throws InvalidRequestException {//TODO: создать DTO

        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            new DialogsResponse(personService.updateEmail(body))
        ));
    }

    private void setupCaptcha(CaptchaDto captchaDto) {
        Captcha captcha = CaptchaUtils.createCaptcha(200, 50);
        captchaDto.setHidden(captcha.getAnswer());
        captchaDto.setImage(CaptchaUtils.encodeBase64(captcha));
    }
}
