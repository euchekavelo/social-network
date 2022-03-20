package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.User;
import ru.skillbox.socnetwork.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public List<User> getAll() {
        return this.userRepository.getAll();
    }

    public User getByEmail(String email) {
        return this.userRepository.getByEmail(email);
    }
}
