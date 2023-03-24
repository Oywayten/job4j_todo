package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Oywayten 15.03.2023.
 */
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private static final String COMPLETE = "UPDATE Task SET done = true WHERE id = :id";
    private static final String DELETE = "DELETE Task WHERE id = :id";
    private static final String GET_ALL = "from Task";
    private static final String GET_ALL_DONE = String.format("%s where done = :done", GET_ALL);
    private final SessionFactory sf;

    @Override
    public List<Task> getAll() {
        Session session = sf.openSession();
        Transaction transaction = null;
        List<Task> taskList = List.of();
        try {
            taskList = session.createQuery(GET_ALL, Task.class).getResultList();
            transaction = session.beginTransaction();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return taskList;
    }

    @Override
    public List<Task> findByStatus(Boolean done) {
        Session session = sf.openSession();
        Transaction transaction = null;
        List<Task> taskList = List.of();
        try {
            transaction = session.beginTransaction();
            taskList = session.createQuery(GET_ALL_DONE, Task.class)
                    .setParameter("done", done).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return taskList;
    }

    @Override
    public Optional<Task> findById(int id) {
        Optional<Task> taskOptional = Optional.empty();
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            taskOptional = Optional.ofNullable(session.find(Task.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return taskOptional;
    }

    @Override
    public Optional<Task> add(Task task) {
        Optional<Task> taskOptional = Optional.empty();
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
            taskOptional = Optional.of(task);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return taskOptional;
    }

    @Override
    public boolean update(Task task) {
        boolean isUpdated;
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(task);
            transaction.commit();
            isUpdated = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return isUpdated;
    }

    @Override
    public boolean complete(int id) {
        boolean isCompleted = false;
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int completed = session.createQuery(COMPLETE).setParameter("id", id).executeUpdate();
            if (completed != 0) {
                transaction.commit();
                isCompleted = true;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return isCompleted;
    }

    @Override
    public boolean delete(int id) {
        boolean isDeleted = false;
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int deleted = session.createQuery(DELETE).setParameter("id", id).executeUpdate();
            if (deleted != 0) {
                transaction.commit();
                isDeleted = true;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return isDeleted;
    }
}
