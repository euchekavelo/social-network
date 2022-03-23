package ru.skillbox.socnetwork.model.rsdto;

import lombok.Data;

import java.util.List;

@Data
public class GeneralListResponse<T> {
    private String error = "string";
    private String errorDesc;
    private Long timestamp;
    private Long total;
    private Integer offset;
    private Integer perPage;
    private List<T> data;
}
