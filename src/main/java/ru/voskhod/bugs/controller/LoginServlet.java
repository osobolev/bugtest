package ru.voskhod.bugs.controller;

import ru.voskhod.bugs.dao.BugsDao;
import ru.voskhod.bugs.dao.BugsDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.OptionalInt;

public class LoginServlet extends HttpServlet {

    private DataSource dataSource;

    public LoginServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try (Connection connection = dataSource.getConnection()) {
            BugsDao dao = new BugsDaoImpl(connection);
            OptionalInt userId = dao.login(login, password);
            if (!userId.isPresent()) {
                // todo: показать ошибку
                resp.sendRedirect("login.html");
            } else {
                req.getSession().setAttribute("userId", userId.getAsInt());
                resp.sendRedirect("app/viewbugs");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
