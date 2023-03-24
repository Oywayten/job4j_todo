package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * Oywayten 23.03.2023.
 */
public interface UserRepository {

    Optional<User> add(User user);

    Optional<User> findUserByLoginAndPassword(String login, String password);
}
