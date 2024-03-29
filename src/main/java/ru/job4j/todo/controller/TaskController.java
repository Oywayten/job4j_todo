package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static ru.job4j.todo.util.Util.setTimezone;
import static ru.job4j.todo.util.Util.setUser;

/**
 * Oywayten 17.03.2023.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    public TaskController(TaskService taskService, CategoryService categoryService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String tasks(final Model model, HttpSession session) {
        model.addAttribute("tasks", taskService.getAll());
        setTimezone(session, model);
        setUser(session, model);
        return "/task/list";
    }

    @GetMapping("/completed")
    public String completedTasks(final Model model, HttpSession session) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        setTimezone(session, model);
        setUser(session, model);
        return "/task/completed-tasks-list";
    }

    @GetMapping("/new")
    public String newTasks(final Model model, HttpSession session) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        setTimezone(session, model);
        setUser(session, model);
        return "/task/new-tasks-list";
    }

    @GetMapping("/add-form")
    public String add(Model model, HttpSession session) {
        setUser(session, model);
        model.addAttribute("categories", categoryService.getAll());
        return "/task/add-form";
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute Task task, @RequestParam("category") List<Integer> categoryIds, HttpSession session) {
        List<Category> categories = categoryService.findByIds(categoryIds);
        if (categories.isEmpty()) {
            setUser(session, model);
            return goToError(model, "Error adding categories to task");
        }
        task.setCategories(categories);
        task.setUser((User) session.getAttribute("user"));
        if (taskService.add(task).isEmpty()) {
            setUser(session, model);
            return goToError(model, MessageFormat.format("Task creation error with title = {0} and description = {1}",
                    task.getTitle(), task.getDescription()));
        }
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String task(Model model, @PathVariable("id") int id, HttpSession session) {
        Optional<Task> task = taskService.findById(id);
        setUser(session, model);
        if (task.isEmpty()) {
            return goToError(model, MessageFormat.format("Error displaying task with id = {0}", id));
        }
        model.addAttribute("task", task.get());
        return "/task/description";
    }

    @GetMapping("/edit-form")
    public String editForm(final Model model, @RequestParam("id") int id, HttpSession session) {
        Optional<Task> optionalTask = taskService.findById(id);
        setUser(session, model);
        if (optionalTask.isEmpty()) {
            return goToError(model, MessageFormat.format("Error show update form for task with id = {0}", id));
        }
        model.addAttribute("task", optionalTask.get());
        return "/task/edit-form";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task, HttpSession session) {
        if (!taskService.update(task)) {
            setUser(session, model);
            return goToError(model, MessageFormat.format("Error updating task with id = {0}", task.getId()));
        }
        return MessageFormat.format("redirect:/tasks/{0}", task.getId());
    }

    @PostMapping("/complete")
    public String complete(Model model, @RequestParam("id") int id, HttpSession session) {
        if (!taskService.complete(id)) {
            setUser(session, model);
            return goToError(model, MessageFormat.format("Error completing task with id = {0}", id));
        }
        return MessageFormat.format("redirect:/tasks/{0}", id);
    }

    @PostMapping("/delete")
    public String delete(Model model, @RequestParam("id") int id, HttpSession session) {
        if (!taskService.delete(id)) {
            setUser(session, model);
            return goToError(model, MessageFormat.format("Error deleting task with id = {0}", id));
        }
        return "redirect:/tasks";
    }

    public String goToError(Model model, String message) {
        model.addAttribute("message", message);
        return "/error";
    }
}
