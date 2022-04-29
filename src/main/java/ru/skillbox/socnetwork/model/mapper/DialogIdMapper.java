package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DialogIdMapper  implements RowMapper<DialogDto> {
    @Override
    public DialogDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        DialogDto dialogDto = new DialogDto();

        dialogDto.setDialogId(rs.getInt("dialog_id"));

        return dialogDto;
    }
}
