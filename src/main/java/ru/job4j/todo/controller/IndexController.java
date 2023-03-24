package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static ru.job4j.todo.util.Util.setUser;

/**
 * Oywayten 23.03.2023.
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        setUser(session, model);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login-page";
    }
}
