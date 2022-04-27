package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogsDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DialogsMapper implements RowMapper<DialogsDto> {
    @Override
    public DialogsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        DialogsDto dialogsDto = new DialogsDto();

        dialogsDto.setDialogId(rs.getInt("dialog_id"));
        dialogsDto.setTime(rs.getLong("time"));
        dialogsDto.setUnreadCount(rs.getInt("unread_count"));
        dialogsDto.setMessageText(rs.getString("message_text"));
        dialogsDto.setReadStatus(rs.getString("read_status"));
        dialogsDto.setMessageId(rs.getInt("message_id"));

        return dialogsDto;
    }
}
