package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

/**
 * Oywayten 17.03.2023.
 */
@Service
@AllArgsConstructor
public class TaskService {

    public final TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.getAll();
    }

    public List<Task> findByStatus(Boolean done) {
        return taskRepository.findByStatus(done);
    }

    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> add(Task task) {
        return taskRepository.add(task);
    }

    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    public boolean complete(int id) {
        return taskRepository.complete(id);
    }

    public boolean delete(int id) {
        return taskRepository.delete(id);
    }
}
