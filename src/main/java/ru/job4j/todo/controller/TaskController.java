package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.service.TaskService;

/**
 * Oywayten 17.03.2023.
 */
@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/tasks")
    public String tasks(final Model model, @RequestParam(name = "done", required = false) Boolean done) {
        model.addAttribute("tasks", taskService.getAll(done));
        return "task/list";
    }

    @GetMapping("/addTaskForm")
    public String addTask() {
        return "task/add-task";
    }

    @PostMapping("/createTask")
    public String createTask() {
        return "redirect:/tasks";
    }
}
