package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import ru.skillbox.socnetwork.model.entity.enums.TypeAction;

import java.time.LocalDateTime;

@Data
public class BlockHistory {
    private int id;
    private long time;
    private int personId;
    private int postId;
    private int commentId;
    private TypeAction action;
}
