package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "FROM User WHERE login = :login AND password = :password";
    private final CrudRepository crudRepository;
    public static final Logger LOG = LoggerFactory.getLogger(HibernateUserRepository.class);

    @Override
    public Optional<User> add(User user) {
        Optional<User> userOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.save(user));
            userOptional = Optional.of(user);
        } catch (HibernateException e) {
            LOG.error("Add user error", e);
        }
        return userOptional;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(FIND_USER_BY_LOGIN_AND_PASSWORD, User.class, Map.of("login", login, "password", password));
    }
}
