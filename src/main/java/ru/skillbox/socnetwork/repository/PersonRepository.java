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

@RequiredArgsConstructor
@Repository
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
}