package ru.skillbox.socnetwork.model.rsdto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CorrectLongResponse<T> {
    private String error = "string";
    private long timestamp = System.currentTimeMillis();
    private int total = 0;
    private int offset = 0;
    private int perPage = 20;
    private T data;
}
