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

public class AddBugServlet extends HttpServlet {

    private DataSource dataSource;

    public AddBugServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            String shortText = req.getParameter("short_text");
            String fullText = req.getParameter("full_text");
            BugsDao dao = new BugsDaoImpl(connection);
            Object maybeUserId = req.getSession().getAttribute("userId");
            if (maybeUserId == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            int userId = ((Integer) maybeUserId).intValue();
            dao.addBug(shortText, fullText, userId);
            resp.sendRedirect("viewbugs");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
