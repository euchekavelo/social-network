package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.Post;
import ru.skillbox.socnetwork.model.mapper.PostMapper;
import ru.skillbox.socnetwork.model.rsdto.postdto.NewPostDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
@DebugLogs
public class PostRepository {
    private final JdbcTemplate jdbc;

    public List<Post> getAll() {
        return jdbc.query("select * from post", new PostMapper());
    }

    public List<Post> getAlreadyPostedWithOffset(int offset, int limit) {
        String sql = "SELECT * FROM post WHERE time < ? ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new PostMapper(), System.currentTimeMillis(), limit, offset);
    }

    public List<Post> getByAuthorIdWithOffset(int authorId, int offset, int limit) {
        String sql = "SELECT * FROM post WHERE author = ? ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new PostMapper(), authorId, limit, offset);
    }

    public Post getById(int id) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM post WHERE id = ?";
        return jdbc.queryForObject(sql, new PostMapper(), id);
    }

    public void deleteById(int id) {
        String sql = "delete from post where id = ?";
        jdbc.update(sql, id);
    }

    public Post getPersonLastPost(int personId) throws EmptyResultDataAccessException {
        String sql = "select * from post where author = ? order by time desc limit 1";
        return jdbc.queryForObject(sql, new PostMapper(), personId);
    }

    public Post addPost(NewPostDto newPostDto) {
        String sql = "insert into post (time, author, title, post_text, is_blocked) values (?, ?, ?, ?, ?)";
        jdbc.update(sql, newPostDto.getTime(), newPostDto.getAuthorId(), newPostDto.getTitle(), newPostDto.getPostText(), false);
        return getPersonLastPost(newPostDto.getAuthorId());
    }

    public void editPost(int id, NewPostDto newPostDto) {
        String sql = "update post set title = ?, post_text = ? where id = ?";
        jdbc.update(sql, newPostDto.getTitle(), newPostDto.getPostText(), id);
    }

    public void updateLikeCount(Integer likes, Integer postId) {
        String sql = "update post set likes = ? where id = ?";
        jdbc.update(sql, likes, postId);
    }

    public Integer getPostCount() {
        String sql = "select count(*) from post";
        return jdbc.queryForObject(sql, (rs, rowNum) -> rs.getInt("count"));
    }

    public List<Post> choosePostsWhichContainsText(String text, long dateFrom, long dateTo, String authorName,
                                                   String authorSurname, int perPage) {


        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", "%" + authorName + "%");
        parameters.addValue("surname", "%" + authorSurname + "%");
        parameters.addValue("text", "%" + text + "%");
        parameters.addValue("dateFrom", dateFrom);
        parameters.addValue("dateTo", dateTo);

        StringBuilder sql = new StringBuilder();
        sql.append("select p.*")
                .append("from post p")
                .append(" join person on p.author = person.id")
                .append(" where ((first_name like :name and last_name like :surname)")
                .append(" or (first_name like :surname and last_name like :name))")
                .append(" and (post_text like :text or title like :text)")
                .append(" and time > :dateFrom")
                .append(" and time < :dateTo")
                .append(" and p.is_blocked = 'f'");

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbc);
        return template.query(sql.toString(), parameters, new PostMapper());
    }

    public void deleteAllPersonPosts(Integer personId) {
        String sql = "DELETE FROM post WHERE author = ?";
        jdbc.update(sql, personId);
    }

    public List<Post> choosePostsWhichContainsTextWithTags(String text, long dateFrom, long dateTo, String authorName,
                                                           String authorSurname, String tags, int perPage) {
//        List<String> tags = new ArrayList<>();
//        tags.add("good");
//        tags.add("прекрасный");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", "%" + authorName + "%");
        parameters.addValue("surname", "%" + authorSurname + "%");
        parameters.addValue("text", "%" + text + "%");
        parameters.addValue("dateFrom", dateFrom);
        parameters.addValue("dateTo", dateTo);
        StringBuilder sql = new StringBuilder();
        sql.append("select p.* ")
                .append("from post p")
                .append(" join person on p.author = person.id")
                .append(" left join post2tag pt on p.id = pt.post_id")
                .append(" left join tag t on pt.tag_id = t.id")
                .append(" where ((first_name like :name and last_name like :surname)")
                .append(" or (first_name like :surname and last_name like :name))")
                .append(" and (post_text like :text or title like :text)")
                .append(" and time > :dateFrom")
                .append(" and time < :dateTo")
                .append(" and p.is_blocked = 'f'")
                .append(tags);

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbc);
        return template.query(sql.toString(), parameters, new PostMapper());
    }
}
