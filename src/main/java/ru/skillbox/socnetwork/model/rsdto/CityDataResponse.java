package ru.skillbox.socnetwork.model.rsdto;

import lombok.Data;

@Data
public class CityDataResponse {
    private int id;
    private String title;
    private String message;

    public CityDataResponse(String message) {
        this.message = message;
    }

    public CityDataResponse(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
