package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

/**
 * Oywayten 15.03.2023.
 */
@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private static final String FROM_CATEGORY = "FROM Category";
    private final CrudRepository crudRepository;

    @Override
    public List<Category> getAll() {
        return crudRepository.query(FROM_CATEGORY, Category.class);
    }

    @Override
    public List<Category> findByIds(List<Integer> ids) {
        return crudRepository.query(String.format("%s WHERE id in (:ids)", FROM_CATEGORY), Category.class, Map.of("ids", ids));
    }
}
