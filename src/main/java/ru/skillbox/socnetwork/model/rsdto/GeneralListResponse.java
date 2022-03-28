//package ru.skillbox.socnetwork.model.rsdto;
//
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//public class GeneralListResponse<T> {
//
//    private String error;
//    private Long timestamp;
//    private int total;
//    private int offset;
//    private int perPage;
//    private List<T> data;
//
//    public GeneralListResponse(List<T> data, int offset, int perPage) {
//        error = "string";
//        timestamp = System.currentTimeMillis();
//        total = data.size();
//        this.offset = offset;
//        this.perPage = perPage;
//        this.data = data;
//    }
//
//}
