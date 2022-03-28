package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import ru.skillbox.socnetwork.model.entity.enums.TypeCode;

import java.time.LocalDateTime;

@Data
public class FriendStatus {
    private int id;
    private LocalDateTime time;
    private String name;
    private TypeCode code;
}
