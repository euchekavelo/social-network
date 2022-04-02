package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.FriendStatus;
import ru.skillbox.socnetwork.model.entity.enums.TypeCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
public class FriendStatusMapper implements RowMapper<FriendStatus> {
    @Override
    public FriendStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendStatus mapper = new FriendStatus();
        mapper.setId(rs.getInt("id"));
        mapper.setTime(getLong(rs.getTimestamp("time")));
        mapper.setName(rs.getString("name"));
        mapper.setCode(TypeCode.valueOf(rs.getString("code")));
        return mapper;
    }

    private Long getLong(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.getTime();
    }
}
