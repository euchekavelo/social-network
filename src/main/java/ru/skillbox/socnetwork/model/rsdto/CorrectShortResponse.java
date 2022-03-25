package ru.skillbox.socnetwork.model.rsdto;

import lombok.Data;

@Data
public class CorrectShortResponse<T> {
    private String error = "string";
    private long timestamp;
    private T data;
}
