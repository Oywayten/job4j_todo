package ru.job4j.todo.util;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import ru.job4j.todo.model.User;

/**
 * Oywayten 23.03.2023.
 */
public class Util {

    private Util() {
    }

    public static void setUser(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
