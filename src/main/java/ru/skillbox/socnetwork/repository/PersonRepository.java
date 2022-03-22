package ru.skillbox.socnetwork.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.repository.mapper.PersonMapper;

import java.util.List;

@Repository
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getListRecommendedFriends() {
         return jdbcTemplate.query("" +
                 "WITH subquery AS (\n" +
                 "\tSELECT f.dst_person_id AS id\n" +
                 "\tFROM friendship f INNER JOIN friendship_status fs ON f.status_id = fs.id\n" +
                 "\tWHERE f.src_person_id = 2 AND fs.code = 'FRIEND'\n" +
                 "\tUNION\n" +
                 "\tSELECT f.src_person_id AS id\n" +
                 "\tFROM friendship f INNER JOIN friendship_status fs ON f.status_id = fs.id\n" +
                 "\tWHERE f.dst_person_id = 2 AND fs.code = 'FRIEND'\n" +
                 ")\n" +
                 "SELECT * \n" +
                 "FROM person\n" +
                 "WHERE id <> 2 AND id NOT IN (SELECT * FROM subquery)\n" +
                 "ORDER BY RANDOM()\n" +
                 "LIMIT 20", new PersonMapper());
    }

}
