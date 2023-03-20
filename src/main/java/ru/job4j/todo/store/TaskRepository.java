package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Oywayten 17.03.2023.
 */
public interface TaskRepository {

    List<Task> getAll();

    List<Task> getAllDone(Boolean done);

    Task findById(int id);

    Task add(Task task);

    boolean update(Task task);

    boolean complete(int id);

    boolean delete(int id);
}
