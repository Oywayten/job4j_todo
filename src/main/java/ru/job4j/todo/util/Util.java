package ru.job4j.todo.util;

import org.springframework.ui.Model;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

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

    public static List<TimeZone> getAvailableTimeZones() {
        return Arrays.stream(TimeZone.getAvailableIDs())
                .map(TimeZone::getTimeZone)
                .sorted(Comparator.comparingInt(TimeZone::getRawOffset))
                .toList();
    }

    public static void setTimezone(HttpSession session, Model model) {
        String timezone = ((User) session.getAttribute("user")).getTimezone();
        if (null == timezone) {
            timezone = TimeZone.getDefault().toZoneId().toString();
        }
        model.addAttribute("timezone",  timezone);
    }
}
