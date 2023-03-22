package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.text.MessageFormat;

/**
 * Oywayten 17.03.2023.
 */
@Controller
@RequestMapping({"/tasks", "/"})
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping
    public String tasks(final Model model) {
        model.addAttribute("tasks", taskService.getAll(null));
        return "task/list";
    }

    @GetMapping("/completed")
    public String completedTasks(final Model model) {
        model.addAttribute("tasks", taskService.getAll(true));
        return "task/completed-tasks-list";
    }

    @GetMapping("/new")
    public String newTasks(final Model model) {
        model.addAttribute("tasks", taskService.getAll(false));
        return "task/new-tasks-list";
    }

    @GetMapping("/{id}")
    public String task(final Model model, @PathVariable("id") int id) {
        Task task = taskService.findById(id);
        if (null == task) {
            return goToError(model, MessageFormat.format("Error displaying task with id = {0}", id));
        }
        model.addAttribute("task", task);
        return "task/description";
    }

    @GetMapping("/addForm")
    public String add() {
        return "task/add";
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute Task task) {
        if (null == taskService.add(task)) {
            return goToError(model, MessageFormat.format("Task creation error with title = {0} and description = {1}",
                    task.getTitle(), task.getDescription()));
        }
        return "redirect:/tasks";
    }

    @GetMapping("/updateForm")
    public String updateForm(final Model model, @RequestParam("id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "task/edit";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task) {
        if (!taskService.update(task)) {
            return goToError(model, MessageFormat.format("Error updating task with id = {0}", task.getId()));
        }
        return MessageFormat.format("redirect:/tasks/{0}", task.getId());
    }

    @PostMapping("/complete")
    public String complete(Model model, @RequestParam("id") int id) {
        if (!taskService.complete(id)) {
            return goToError(model, MessageFormat.format("Error completing task with id = {0}", id));
        }
        return MessageFormat.format("redirect:/tasks/{0}", id);
    }

    @PostMapping("/delete")
    public String delete(Model model, @RequestParam("id") int id) {
        if (!taskService.delete(id)) {
            return goToError(model, MessageFormat.format("Error deleting task with id = {0}", id));
        }
        return "redirect:/tasks";
    }

    @GetMapping("/goToError")
    public String goToError(Model model, String message) {
        model.addAttribute("message", message);
        return "/error";
    }
}
