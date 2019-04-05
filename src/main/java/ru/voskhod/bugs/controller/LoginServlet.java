package ru.voskhod.bugs.controller;

import freemarker.template.TemplateException;
import ru.voskhod.bugs.dao.BugsDao;
import ru.voskhod.bugs.dao.BugsDaoImpl;
import ru.voskhod.bugs.view.TemplateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;

public class LoginServlet extends HttpServlet {

    private DataSource dataSource;

    public LoginServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String, Object> params = new HashMap<>();
            TemplateUtil.render("login.ftl", params, resp);
        } catch (TemplateException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try (Connection connection = dataSource.getConnection()) {
            BugsDao dao = new BugsDaoImpl(connection);
            OptionalInt userId = dao.login(login, password);
            if (!userId.isPresent()) {
//                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
//                resp.sendRedirect("login.html");
                Map<String, Object> params = new HashMap<>();
                String text_error = "Ошибка в логине или пароле";
                params.put("error", text_error);
                TemplateUtil.render("login.ftl", params, resp);
            } else {
                req.getSession().setAttribute("userId", userId.getAsInt());
                resp.sendRedirect("app/viewbugs");
            }
        } catch (SQLException | TemplateException e) {
            throw new ServletException(e);
        }
    }
}
