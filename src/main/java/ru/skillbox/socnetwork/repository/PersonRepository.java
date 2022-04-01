package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.mapper.PersonMapper;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final JdbcTemplate jdbc;

    public Person getById(int id) {
        String sql = "select * from person where id = ?";
        return jdbc.queryForObject(sql, new PersonMapper(), id);
    }

    public Person getByEmail(String email) {
        String sql = "select * from person where e_mail = ?";
        return jdbc.queryForObject(sql, new PersonMapper(), email);
    }

    public boolean isEmptyEmail(String email) {
        try {
            getByEmail(email);
        } catch (DataAccessException e) {
            return true;
        }
        return false;
    }

    public Person getPersonAfterLogin(LoginDto loginDto) {
        Person person;
        try {
            person = getByEmail(loginDto.getEmail());
        } catch (DataAccessException e) {
            return null;
        }
        return person;
    }

    public List<Person> getAll() {
        return jdbc.query("select * from person", new PersonMapper());
    }

    public Person saveFromRegistration(Person person) {
        person.setRegDate(System.currentTimeMillis());
        String sql = "insert into person (first_name, last_name, reg_date, e_mail, password) values (?, ?, ?, ?, ?)";
        jdbc.update(sql,
                person.getFirstName(),
                person.getLastName(),
                person.getRegDate(),
                person.getEmail(),
                person.getPassword());
        return person;
    }

    public List<Person> getListRecommendedFriends() {
        return jdbc.query("" +
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