package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Oywayten 15.03.2023.
 */
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private static final String COMPLETE = "UPDATE Task SET done = true WHERE id = :id";
    private static final String DELETE = "DELETE Task WHERE id = :id";
    private static final String JOIN_FETCH_PRIORITY = "JOIN FETCH t.priority";
    private static final String GET_ALL = "from Task t";
    private static final String GET_ALL_WITH_PRIORITY = String.format("%s %s", GET_ALL, JOIN_FETCH_PRIORITY);
    private static final String GET_ALL_DONE_WITH_PRIORITY = String.format("%s where t.done = :done", GET_ALL_WITH_PRIORITY);
    private final CrudRepository crudRepository;

    @Override
    public List<Task> getAll() {
        return crudRepository.query(GET_ALL_WITH_PRIORITY, Task.class);
    }

    @Override
    public List<Task> findByStatus(Boolean done) {
        return crudRepository.query(GET_ALL_DONE_WITH_PRIORITY, Task.class, Map.of("done", done));
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(crudRepository.tx(session -> session.find(Task.class, id)));
    }

    @Override
    public Optional<Task> add(Task task) {
        return crudRepository.tx(session -> {
            session.persist(task);
            return Optional.ofNullable(task);
        });
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.tx(session -> {
            session.merge(task);
            return true;
        });

    }

    @Override
    public boolean complete(int id) {
        return crudRepository.run(COMPLETE, Map.of("id", id)) == 1;
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.run(DELETE, Map.of("id", id)) == 1;
    }
}
