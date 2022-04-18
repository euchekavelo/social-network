package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FriendshipPersonDto {

    @JsonProperty("user_id")
    private Integer userId;
    private String status;

}
