package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DialogMapper implements RowMapper<DialogDto> {
    @Override
    public DialogDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        DialogDto dialogDto = new DialogDto();

        dialogDto.setId(rs.getInt("id"));
        dialogDto.setAuthorId(rs.getInt("author_id"));
        dialogDto.setRecipientId(rs.getInt("recipient_id"));

        return dialogDto;
    }
}