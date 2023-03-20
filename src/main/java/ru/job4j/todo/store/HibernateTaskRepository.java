package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Oywayten 15.03.2023.
 */
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final SessionFactory sf;

    @Override
    public List<Task> getAll() {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        List<Task> taskList = session.createQuery("from Task t", Task.class).getResultList();
        try {
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return taskList;
    }

    @Override
    public List<Task> getAllDone(Boolean done) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        List<Task> taskList = session.createQuery("from Task t where t.done = :done", Task.class)
                .setParameter("done", done).getResultList();
        try {
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return taskList;
    }

    @Override
    public Task findById(int id) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Task task = session.find(Task.class, id);
        try {
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return task;
    }

    public Task save(Task task) {
        boolean isSave = false;
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (task.getId() == 0) {
                session.persist(task);
            } else {
                session.update(task);
            }
            transaction.commit();
            isSave = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isSave ? null : findById(task.getId());
    }

    @Override
    public boolean complete(int id) {
        boolean isComleted = false;
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Task task = session.find(Task.class, id);
        task.setDone(true);
        try {
            transaction.commit();
            isComleted = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isComleted;
    }

    @Override
    public boolean delete(int id) {
        boolean isDeleted = false;
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.getReference(Task.class, id));
        try {
            transaction.commit();
            isDeleted = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isDeleted;
    }
}
