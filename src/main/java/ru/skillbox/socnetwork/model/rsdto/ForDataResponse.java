package ru.skillbox.socnetwork.model.rsdto;

import lombok.Data;

@Data
public class ForDataResponse {
    private int id;
    private String title;
    private String message;

    public ForDataResponse(String message) {
        this.message = message;
    }

    public ForDataResponse(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
