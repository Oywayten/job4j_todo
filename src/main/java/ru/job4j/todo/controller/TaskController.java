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

    @GetMapping("/tasks/{id}")
    public String task(final Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "task/description";
    }

    @GetMapping("/addTaskForm")
    public String addTask() {
        return "task/add";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/formUpdateTask/{id}")
    public String createTask(final Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "task/update";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskService.update(task);
        return MessageFormat.format("redirect:/tasks/{0}", task.getId());
    }

    @PostMapping("/completeTask")
    public String completeTask(@RequestParam("id") int id) {
        taskService.complete(id);
        return MessageFormat.format("redirect:/tasks/{0}", id);
    }

    @PostMapping("/deleteTask")
    public String addTask(@RequestParam("id") int id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }
}
