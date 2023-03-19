package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskRepository;

import java.util.List;

/**
 * Oywayten 17.03.2023.
 */
@Service
@AllArgsConstructor
public class TaskService {

    public final TaskRepository taskRepository;

    public List<Task> getAll(Boolean done) {
        return done == null ? taskRepository.getAll() : taskRepository.getAllDone(done);
    }
}
