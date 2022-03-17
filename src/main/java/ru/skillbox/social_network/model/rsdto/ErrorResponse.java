package ru.skillbox.social_network.model.rsdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String error = "invalid_request";
    private String error_description = "string";
}
