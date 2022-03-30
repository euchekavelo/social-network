package ru.skillbox.socnetwork.model.rsdto;

import lombok.Data;

import java.util.List;

@Data
public class GeneralListResponse<T> {

    private String error;
    private Long timestamp;
    private Integer total;
    private Integer offset;
    private Integer perPage;
    private List<T> data;

    public GeneralListResponse(List<T> data, Integer offset, Integer perPage) {
        error = "string";
        timestamp = System.currentTimeMillis();
        total = data.size();
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }

}
