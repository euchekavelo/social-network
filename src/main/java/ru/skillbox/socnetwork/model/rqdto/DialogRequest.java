package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DialogRequest {
    @JsonProperty("users_ids")
    private List<Integer> userIds;
}
