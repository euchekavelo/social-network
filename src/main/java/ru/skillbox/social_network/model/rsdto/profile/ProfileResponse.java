package ru.skillbox.social_network.model.rsdto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponse {

    private String error;
    private long timestamp;
    private DataResponse data;
}
