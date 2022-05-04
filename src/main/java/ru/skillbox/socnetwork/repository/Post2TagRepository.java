package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Post2Tag;
import ru.skillbox.socnetwork.model.mapper.Post2TagMapper;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class Post2TagRepository {
    private final JdbcTemplate jdbc;

    public void addTag2Post(int postId, int tagId) {
        String sql = "insert into post2tag (post_id, tag_id) values (?, ?)";
        jdbc.update(sql, postId, tagId);
    }

    public List<Post2Tag> getPostTags(int postId) {
        String sql = "SELECT * FROM post2tag WHERE post_id = ?";
        return jdbc.query(sql, new Post2TagMapper(), postId);
    }

    public void deletePostTags(int postId) {
        String sql = "delete from post2tag where post_id = ?";
        jdbc.update(sql, postId);
    }

    public void deleteAllPersonTags(Integer personId){
        String sql = "DELETE " +
                "FROM post2tag " +
                "WHERE post_id " +
                "IN (SELECT id " +
                "FROM post " +
                "WHERE author = ?)";
        jdbc.update(sql, personId);
    }
}
