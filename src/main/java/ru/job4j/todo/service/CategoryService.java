package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;

/**
 * Oywayten 30.03.2023.
 */
@Service
@AllArgsConstructor
public class CategoryService {

    public final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    public List<Category> findByIds(List<Integer> ids) {
        return categoryRepository.findByIds(ids);
    }
}
