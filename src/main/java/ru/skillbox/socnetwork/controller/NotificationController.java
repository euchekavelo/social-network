package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;
import ru.skillbox.socnetwork.model.rsdto.NotificationDtoToView;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.service.NotificationService;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
@InfoLogs
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping()
    public ResponseEntity<GeneralResponse<List<NotificationDtoToView>>> getNotificationsByUser(
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            @RequestParam(value = "itemPerPage", defaultValue = "20", required = false) int perPage) {

        List<NotificationDtoToView> notifications = notificationService.getAllNotifications();
        GeneralResponse<List<NotificationDtoToView>> response = new GeneralResponse<>(notifications);

        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<GeneralResponse<Object>> getNotifications(
            @RequestParam(value = "id", defaultValue = "0", required = false) int id,
            @RequestParam(value = "all", defaultValue = "true", required = false) boolean all) {

        notificationService.readAllNotifications(id, all);
        return ResponseEntity.ok(new GeneralResponse<Object>());
    }

}
