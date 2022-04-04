package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Message;
import ru.skillbox.socnetwork.model.mapper.MessageMapper;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MessageRepository {
    private final JdbcTemplate jdbc;

    public List<Message> getLastMessageByEmail(String email) {
        String sql = "SELECT * FROM message " +
                "WHERE message";

        return jdbc.query(sql, new MessageMapper(), email);
    }

    public List<Message> getByEmail(String email, String query, Integer offset, Integer itemPerPage) {
        String sql = "SELECT * FROM message " +
                "LEFT JOIN person ON message.author_id = person.id " +
                "WHERE person.e_mail LIKE ?" +
                "OR WHERE message.message_text LIKE ?" +
                "ORDER BY message.recipient_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbc.query(sql, new MessageMapper(), email, query, offset, itemPerPage);
    }

    public List<Message> getDialogsLastMessageByEmail(String email, String query, Integer offset, Integer itemPerPage) {
        String sql = "SELECT * FROM message " +
                "LEFT JOIN person ON message.author_id = person.id " +
                "WHERE person.e_mail LIKE ?" +
                "OR WHERE message.message_text LIKE ?" +
                "GROUP BY message.recipient_id" +
                "HAVING BY MAX(message.time)" +
                "ORDER BY message.time DESC" +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbc.query(sql, new MessageMapper(),email, query, offset, itemPerPage);
    }
}
