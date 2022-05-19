package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class GeneralResponse<T> {
    private Integer id;
    private String title;
    private String path;
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    private Long timestamp;
    private Integer total;
    private Integer offset;
    private Integer perPage;
    private T data;

    public GeneralResponse(String path, String error, String errorDescription, long timestamp) {
        this.path = path;
        this.error = error;
        this.errorDescription = errorDescription;
        this.timestamp = timestamp;
    }

    public GeneralResponse(String error, long timestamp, int total, int offset, int perPage, T data) {
        this.error = error;
        this.timestamp = timestamp;
        this.total = total;
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }

    public GeneralResponse(T data, int total, int offset, int perPage) {
        this.error = "string";
        this.timestamp = System.currentTimeMillis();
        this.total = total;
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }

    public GeneralResponse(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public GeneralResponse(String error, long timestamp, T data) {
        this.error = error;
        this.timestamp = timestamp;
        this.data = data;
    }

    public GeneralResponse(T data) {
        this("string", System.currentTimeMillis(), 20, 0, 20, data);
    }
}
