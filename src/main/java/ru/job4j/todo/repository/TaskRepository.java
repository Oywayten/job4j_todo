package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Oywayten 17.03.2023.
 */
public interface TaskRepository {

    List<Task> findAll();

    List<Task> findByStatus(Boolean done);

    Optional<Task> findById(int id);

    Optional<Task> add(Task task);

    boolean update(Task task);

    boolean complete(int id);

    boolean delete(int id);
}
