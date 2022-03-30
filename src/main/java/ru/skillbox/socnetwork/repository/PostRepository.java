package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Post;
import ru.skillbox.socnetwork.model.mapper.PostMapper;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    private final JdbcTemplate jdbc;

    public List<Post> getAll(){
        return jdbc.query("select * from post", new PostMapper());
    }
}
