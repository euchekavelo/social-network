package ru.skillbox.socnetwork.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PersonTemp {
    private Integer id;
    @JsonProperty("first_name")
    @Schema(example = "Иван")
    private String firstName;
    @JsonProperty("last_name")
    @Schema(example = "Иванов")
    private String lastName;
    @Schema(example = "https://host.ru/image.jpg")
    private String photo;

    public PersonTemp(Integer id, String firstName, String lastName, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }
}


