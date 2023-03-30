package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * Oywayten 30.03.2023.
 */
public interface CategoryRepository {

    List<Category> getAll();

    List<Category> findByIds(List<Integer> categoryIds);
}
