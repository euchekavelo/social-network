package ru.skillbox.socnetwork.model.entity;

import lombok.Data;

@Data
public class Friendship {
    private Integer id;
    private Integer statusId;
    private Integer srcPersonId;
    private Integer dstPersonId;
}
