package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.PostLike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
public class PostLikeMapper implements RowMapper<PostLike> {
    @Override
    public PostLike mapRow(ResultSet rs, int rowNum) throws SQLException {
        PostLike mapper = new PostLike();
        mapper.setId(rs.getInt("id"));
        mapper.setTime(getLong(rs.getTimestamp("time")));
        mapper.setPostId(rs.getInt("post_id"));
        mapper.setPersonId(rs.getInt("post_id"));
        return mapper;
    }

    private Long getLong(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.getTime();
    }
}