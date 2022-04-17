package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountMapper implements RowMapper<DialogsResponse> {

    @Override
    public DialogsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        DialogsResponse dialogsResponse = new DialogsResponse();

        dialogsResponse.setCount(rs.getInt("unread_count"));

        return dialogsResponse;
    }
}
