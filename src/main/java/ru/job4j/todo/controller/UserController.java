package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.todo.util.Util.getAvailableTimeZones;

/**
 * Oywayten 22.03.2023.
 */
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService service) {
        userService = service;
    }

    @GetMapping("/add-user")
    public String addUser(Model model) {
        model.addAttribute("zones", getAvailableTimeZones());
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (userService.add(user).isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "User with this login already exists");
            return "redirect:/users/add-user";
        }
        userService.add(user);
        return "redirect:/tasks";
    }

    /*
    /login-page?fail=false
     */
    @GetMapping("/login-page")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByLoginAndPassword(
                user.getLogin(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/users/login-page?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        session.setAttribute("timezone", userDb.get().getTimezone());
        return "redirect:/index";
    }
}
