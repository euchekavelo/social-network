package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class City {

    private Integer id;
    private Integer countryId;
    private String name;
}
