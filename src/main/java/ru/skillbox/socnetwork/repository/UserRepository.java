package ru.skillbox.socnetwork.repository;

import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.User;

import java.util.List;

@Repository
public class UserRepository {
    private List<User> users;

    public User getByEmail (String email) {
        return this.users.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAll() {
        return this.users;
    }
}
