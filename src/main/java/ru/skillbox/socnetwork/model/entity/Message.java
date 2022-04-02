package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import ru.skillbox.socnetwork.model.entity.enums.TypeReadStatus;

import java.time.LocalDateTime;

@Data
public class Message {
    private Integer id;
    private Long time;
    private Integer authorId;
    private Integer recipientId;
    private String messageText;
    private TypeReadStatus readStatus;
}
