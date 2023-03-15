package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Oywayten 15.03.2023.
 */
@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;
}
