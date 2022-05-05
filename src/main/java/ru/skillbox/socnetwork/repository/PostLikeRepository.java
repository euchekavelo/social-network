package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.PostLike;
import ru.skillbox.socnetwork.model.mapper.PostLikeMapper;

import java.util.List;

@RequiredArgsConstructor
@Repository
@DebugLogs
public class PostLikeRepository {
    private final JdbcTemplate jdbc;

    public List<PostLike> getPostLikes(int itemId) {
        String sql = "SELECT * FROM post_like WHERE post_id = ?";
        return jdbc.query(sql, new PostLikeMapper(), itemId);
    }

    public PostLike getPersonLike(Integer personId, Integer itemId) {
        String sql = "SELECT * FROM post_like WHERE post_id = ? AND person_id = ?";
        try {
            return jdbc.queryForObject(sql, new PostLikeMapper(), itemId, personId);
        } catch (DataAccessException e){
            return null;
        }
    }

    public void addLike(Integer personId, Integer itemId) {
        String sql = "INSERT INTO post_like (time, person_id, post_id) values (?, ?, ?)";
        jdbc.update(sql, System.currentTimeMillis(), personId, itemId);
    }

    public boolean getIsLiked(Integer currentPersonId, Integer itemId) {
        return getPersonLike(currentPersonId, itemId) != null;
    }

    public void deleteLike(Integer currentPersonId, int itemId) {
        String sql = "DELETE FROM post_like WHERE person_id = ? AND post_id = ?";
        jdbc.update(sql, currentPersonId, itemId);
    }

    public void deleteAllPersonLikes(Integer personId){
        String sql = "delete from post_like where person_id = ?";
        jdbc.update(sql, personId);
    }

    public void deleteAllPersonPostsLikes(Integer personId){
        String sql = "DELETE " +
                "FROM post_like " +
                "WHERE post_id " +
                "IN (SELECT id " +
                "FROM post " +
                "WHERE author = ?)";
        jdbc.update(sql, personId);
    }
}
