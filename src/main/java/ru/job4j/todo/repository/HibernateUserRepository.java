package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * Oywayten 22.03.2023.
 */
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "from User where login = :login and password = :password";
    private final SessionFactory sf;

    @Override
    public Optional<User> add(User user) {
        Optional<User> userOptional = Optional.empty();
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            userOptional = Optional.of(user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return userOptional;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        Optional<User> optionalUser = Optional.empty();
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            optionalUser = session.createQuery(FIND_USER_BY_LOGIN_AND_PASSWORD, User.class)
                    .setParameter("login", login).setParameter("password", password).uniqueResultOptional();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return optionalUser;
    }
}
