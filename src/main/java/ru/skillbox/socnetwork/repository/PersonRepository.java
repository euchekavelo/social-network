package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.controller.exception.InvalidRequestException;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.mapper.PersonMapper;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;

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
        String sql = "select * from person where e_mail like ?";
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

    public Person getPersonAfterLogin(LoginDto loginDto) throws InvalidRequestException {
        Person person;
        try {
            person = getByEmail(loginDto.getEmail());
        } catch (DataAccessException e) {
            throw new InvalidRequestException("invalid login");
        }
        if (!loginDto.checkPassword(person.getPassword())) {
            throw new InvalidRequestException("invalid password");
        }
        return person;
    }

    public List<Person> getAll() {
        return jdbc.query("select * from person", new PersonMapper());
    }

    /**
     * @param person for registration. Put all parameters to DB
     * @return person with default photo and with current time
     * @author Alexander Luzyanin
     */
    public Person saveFromRegistration(Person person) {
        person.setRegDate(System.currentTimeMillis());
        String sql = "insert into person (first_name, last_name, reg_date, e_mail, password, photo) values (?, ?, ?, ?, ?, ?)";
        jdbc.update(sql,
                person.getFirstName(),
                person.getLastName(),
                System.currentTimeMillis(),
                person.getEmail(),
                person.getPassword(),
                person.getPhoto());
        return person;
    }

    public List<Person> getListRecommendedFriends(String email) {
        return jdbc.query("" +
                "WITH authorized_person_id as (\n" +
                "\tSELECT p.id \n" +
                "\tFROM person p\n" +
                "\tWHERE p.e_mail = ?\t\n" +
                "),\n" +
                "friends_ids AS (\n" +
                "\tSELECT f.dst_person_id AS id\n" +
                "\tFROM friendship f\n" +
                "\tWHERE f.src_person_id = (SELECT * FROM authorized_person_id) AND f.code IN ('FRIEND', 'REQUEST')\n" +
                "\tUNION\n" +
                "\tSELECT f.src_person_id AS id\n" +
                "\tFROM friendship f\n" +
                "\tWHERE f.dst_person_id = (SELECT * FROM authorized_person_id) AND f.code = 'FRIEND'\n" +
                ")\n" +
                "SELECT * \n" +
                "FROM person\n" +
                "WHERE id <> (SELECT * FROM authorized_person_id) and id NOT IN (SELECT * FROM friends_ids)\n" +
                "ORDER BY RANDOM()\n" +
                "LIMIT 20", new PersonMapper(), email);
    }

    public List<Person> getUserFriends(String email) {
        return jdbc.query("" +
                "WITH authorized_person_id as (\n" +
                "\tSELECT p.id \n" +
                "\tFROM person p\n" +
                "\tWHERE p.e_mail = ?\n" +
                "),\n" +
                "friends_ids AS (\n" +
                "\tSELECT f.dst_person_id AS id\n" +
                "\tFROM friendship f\n" +
                "\tWHERE f.src_person_id = (SELECT * FROM authorized_person_id) AND f.code = 'FRIEND'\n" +
                "\tUNION\n" +
                "\tSELECT f.src_person_id AS id\n" +
                "\tFROM friendship f\n" +
                "\tWHERE f.dst_person_id = (SELECT * FROM authorized_person_id) AND f.code = 'FRIEND'\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM person p\n" +
                "WHERE p.id IN (SELECT * FROM friends_ids)\n" +
                "ORDER BY p.last_name, p.first_name", new PersonMapper(), email);
    }

    public List<Person> getListIncomingFriendRequests(String email) {
        return jdbc.query("" +
                "WITH authorized_person_id as (\n" +
                "\tSELECT p.id\n" +
                "\tFROM person p\n" +
                "\tWHERE p.e_mail = ?\n" +
                "),\n" +
                "persons_request_ids AS (\n" +
                "\tSELECT f.src_person_id AS id\n" +
                "\tFROM friendship f\n" +
                "\tWHERE f.dst_person_id = (SELECT * FROM authorized_person_id) AND f.code = 'REQUEST'\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM person p\n" +
                "WHERE p.id IN (SELECT * FROM persons_request_ids)\n" +
                "ORDER BY p.last_name, p.first_name", new PersonMapper(), email);
    }

    public Person updatePerson(Person person) {
        String sql = "update person set (first_name, last_name, birth_date, phone, about, city, country) = (?, ?, ?, ?, ?, ?, ?) where person.e_mail = ?";
        jdbc.update(sql,
                person.getFirstName(),
                person.getLastName(),
                person.getBirthDate(),
                person.getPhone(),
                person.getAbout(),
                person.getCity(),
                person.getCountry(),
                person.getEmail());
        return person;
    }

    public void updatePhoto(Person person) {
        String sql = "update person set photo = ? where person.e_mail = ?";
        jdbc.update(sql,
                person.getPhoto(),
                person.getEmail());
    }

    public void updatePassword(Person person) {
        String sql = "update person set password = ? where person.e_mail = ?";
        jdbc.update(sql,
                person.getPassword(),
                person.getEmail());
    }

    public void updateEmail(Person person, String email) {
        String sql = "update person set e_mail = ? where person.e_mail = ?";
        jdbc.update(sql,
                email,
                person.getEmail());
    }

    /**
     * TODO build correct country and city
     */
    public List<Person> getPersonsFromSearch(String firstName, String lastName,
                                             long ageFrom, long ageTo,
                                             int countryId, int cityId,
                                             int perPage) {
        long milliSecInYear = 31718612432L;
        long currentTime = System.currentTimeMillis();
        long dateTo = currentTime - ageFrom * milliSecInYear;
        long dateFrom = currentTime - ageTo * milliSecInYear;


        firstName = "%" + firstName + "%";
        lastName = "%" + lastName + "%";
        String sql = "select * from person where first_name like ? and last_name like ? " +
                "and birth_date >= ? and birth_date <= ? " +
                "limit ?";
        return jdbc.query(sql, new PersonMapper(), firstName, lastName, dateFrom, dateTo, perPage);
    }

    public void delete(Integer id) {
        String sql = "delete from person where id = ?";
        Object[] args = new Object[]{id};
        jdbc.update(sql, id);
    }
}