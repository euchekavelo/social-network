package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CaptchaDto {

    private Integer id = (int) (Math.random() * 10000);
    @JsonIgnore
    private String hidden;
    private String image;
}