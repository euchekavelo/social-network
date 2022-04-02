package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {
    private Integer id;
    private Integer typeId;
    private Long sentTime;
    private Integer personId;
    private Integer notificationTypeId;
    private String contact;
}
