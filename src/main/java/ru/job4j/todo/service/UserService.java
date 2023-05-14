package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.Optional;

/**
 * Oywayten 22.03.2023.
 */
@Service
@ThreadSafe
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> add(User user) {
        return repository.add(user);
    }

    public Optional<User> findByLoginAndPassword(String email, String pass) {
        return repository.findByLoginAndPassword(email, pass);
    }
}
