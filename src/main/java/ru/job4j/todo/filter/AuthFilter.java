package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Oywayten 22.03.2023.
 */
@Component
public class AuthFilter implements Filter {

    private static final Set<String> ALLOWED_PAGE_SET = Set.of(
            "login-page",
            "login",
            "add-user",
            "registration",
            "index");

    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (pageCheck(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/users/login-page");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean pageCheck(String uri) {
        return ALLOWED_PAGE_SET.stream().anyMatch(uri::contains);
    }
}
