package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogsDto;
import ru.skillbox.socnetwork.model.rsdto.MessageDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorAndRecipientMapper implements RowMapper<MessageDto> {

    @Override
    public MessageDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        MessageDto messageDto = new MessageDto();

        messageDto.setAuthorId(rs.getInt("author_id"));
        messageDto.setRecipientId(rs.getInt("recipient_id"));

        return messageDto;
    }
}
