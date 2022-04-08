package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import ru.skillbox.socnetwork.model.entity.enums.TypeCode;

@Data
public class Friendship {
    private Integer id;
    private Integer srcPersonId;
    private Integer dstPersonId;
    private Long time;
    private TypeCode code;
}
