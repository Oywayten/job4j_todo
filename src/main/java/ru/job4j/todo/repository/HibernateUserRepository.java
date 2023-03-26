package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

/**
 * Oywayten 22.03.2023.
 */
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "from User where login = :login and password = :password";
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> add(User user) {
        Optional<User> userOptional;
        try {
            crudRepository.run(session -> session.save(user));
            userOptional = Optional.of(user);
        } catch (HibernateException e) {
            userOptional = Optional.empty();
        }
        return userOptional;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return crudRepository.optional(FIND_USER_BY_LOGIN_AND_PASSWORD, User.class, Map.of("login", login, "password", password));
    }
}
