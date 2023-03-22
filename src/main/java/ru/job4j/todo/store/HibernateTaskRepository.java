package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Oywayten 15.03.2023.
 */
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private static final Logger log = LoggerFactory.getLogger(HibernateTaskRepository.class);
    private final SessionFactory sf;

    @Override
    public List<Task> getAll() {
        Session session = sf.openSession();
        Transaction transaction = null;
        List<Task> taskList = List.of();
        try {
            taskList = session.createQuery("from Task t", Task.class).getResultList();
            transaction = session.beginTransaction();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info(e.getMessage());
        } finally {
            session.close();
        }
        return taskList;
    }

    @Override
    public List<Task> getAllDone(Boolean done) {
        Session session = sf.openSession();
        Transaction transaction = null;
        List<Task> taskList = List.of();
        try {
            transaction = session.beginTransaction();
            taskList = session.createQuery("from Task t where t.done = :done", Task.class)
                    .setParameter("done", done).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info(e.getMessage());
        } finally {
            session.close();
        }
        return taskList;
    }

    @Override
    public Task findById(int id) {
        Session session = sf.openSession();
        Transaction transaction = null;
        Task task = null;
        try {
            transaction = session.beginTransaction();
            task = session.find(Task.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info(e.getMessage());
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public Task add(Task task) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info(e.getMessage());
        } finally {
            session.close();
        }
        return task.getId() == 0 ? null : task;
    }

    @Override
    public boolean update(Task task) {
        boolean isUpdated = false;
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
            log.info(e.getMessage());
        } finally {
            session.close();
        }
        return isUpdated;
    }

    @Override
    public boolean complete(int id) {
        boolean isComleted = false;
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int completed = session.createQuery("UPDATE Task SET done = true WHERE id = :id").setParameter("id", id).executeUpdate();
            if (completed != 0) {
                transaction.commit();
                isComleted = true;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info(e.getMessage());
        } finally {
            session.close();
        }
        return isComleted;
    }

    @Override
    public boolean delete(int id) {
        boolean isDeleted = false;
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int deleted = session.createQuery("DELETE Task WHERE id = :id").setParameter("id", id).executeUpdate();
            if (deleted != 0) {
                transaction.commit();
                isDeleted = true;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info(e.getMessage());
        } finally {
            session.close();
        }
        return isDeleted;
    }
}
