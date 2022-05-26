package ru.skillbox.socnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.exception.ErrorResponseDto;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.entity.PersonTemp;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.NotificationDTONew;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.service.NotificationService;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
@InfoLogs
@Tag(name="notifications", description="Взаимодействие с уведомлениями")
public class NotificationController {

    private final JwtTokenProvider tokenProvider;
    private final NotificationService notificationService;
    private final PersonService personService;


    @GetMapping()
    @Operation(summary = "Получение списка уведомлений",
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
            @ApiResponse(responseCode = "200", description = "Успешное получение списка уведомлений",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = GeneralResponse.class)
                    )))
        })
    public ResponseEntity<GeneralResponse<List<NotificationDTONew>>> getNotificationsByUser(
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            @RequestParam(value = "itemPerPage", defaultValue = "20", required = false) int perPage) {
        Person person1 = personService.getById(1);
        PersonTemp person = new PersonTemp(person1.getId(), person1.getFirstName(),
                person1.getLastName(), person1.getPhoto());

        List<NotificationDTONew> notification = new ArrayList<>();
        notification.add(new NotificationDTONew(1, "Новый пост", System.currentTimeMillis(),
                person, "e-mail"));
        notification.add(new NotificationDTONew(2, "Новый пост", System.currentTimeMillis(),
                person, "нe-mail"));

//        GeneralResponse<List<NotificationDto>> response = new GeneralResponse<>
//                (notificationService.getNotifications(offset, perPage));
        GeneralResponse<List<NotificationDTONew>> response = new GeneralResponse<>(notification);

        return ResponseEntity.ok(response);
    }

    @PutMapping()
    @Operation(summary = "Отметить уведомление прочитанным",
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
            @ApiResponse(responseCode = "200", description = "Успешная отметка",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = GeneralResponse.class)
                    )))
        })
    public ResponseEntity<GeneralResponse<List<NotificationDTONew>>> getNotifications(
            @RequestParam(value = "id", defaultValue = "1", required = false) int id,
            @RequestParam(value = "all", defaultValue = "true", required = false) boolean all) {
        Person person1 = personService.getById(1);
        PersonTemp person = new PersonTemp(person1.getId(), person1.getFirstName(),
                person1.getLastName(), person1.getPhoto());

        List<NotificationDTONew> notification = new ArrayList<>();
        notification.add(new NotificationDTONew(1, "Новый пост", System.currentTimeMillis(),
                person, "e-mail"));
        notification.add(new NotificationDTONew(2, "Новый пост", System.currentTimeMillis(),
                person, "нe-mail"));

//        GeneralResponse<List<NotificationDto>> response = new GeneralResponse<>
//                (notificationService.getNotifications(offset, perPage));
        GeneralResponse<List<NotificationDTONew>> response = new GeneralResponse<>(notification);

        return ResponseEntity.ok(response);
    }

}
