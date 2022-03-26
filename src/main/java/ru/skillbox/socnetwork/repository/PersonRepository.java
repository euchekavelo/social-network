package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.mapper.PersonMapper;
import ru.skillbox.socnetwork.model.rsdto.CorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.OkMessage;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final JdbcTemplate jdbc;

    public Person getById(int id) {
        return jdbc.queryForObject("select * from person where id =" + id, new PersonMapper());
    }

    public Person getByEmail(String email) {
        return jdbc.queryForObject("select * from person where e_mail = '" + email + "'", new PersonMapper());
    }

    public boolean isEmptyEmail(String email) {
        return jdbc.query("select * from person where e_mail = '" + email + "'", new PersonMapper()).size() == 0;
    }

    public List<Person> getAll() {
        return jdbc.query("select * from person", new PersonMapper());
    }

    public CorrectShortResponse<OkMessage> saveFromRegistration(Person person) {
        person.setRegDate(LocalDateTime.now());
        jdbc.execute("insert into person (first_name, last_name, reg_date, e_mail, password) values ('" +
                person.getFirstName() + "', '" +
                person.getLastName() + "', '" +
                person.getRegDate() + "', '" +
                person.getEmail() + "', '" +
                person.getPassword() + "')");
        CorrectShortResponse<OkMessage> response = new CorrectShortResponse<>();
        response.setTimestamp(person.getRegDate().toLocalDate().toEpochDay());
        response.setData(new OkMessage());
        return response;
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