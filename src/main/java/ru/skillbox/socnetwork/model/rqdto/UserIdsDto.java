package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserIdsDto {

    @JsonProperty("user_ids")
    private List<Integer> userIds;

}
