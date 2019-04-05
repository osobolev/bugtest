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

public class MoveBugServlet extends HttpServlet {

    private DataSource dataSource;

    public MoveBugServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = dataSource.getConnection()) {

            String bugId = req.getParameter("bugId");
            String newStateId = req.getParameter("newStateId");
            System.out.println(bugId);
            System.out.println(newStateId);

            BugsDao dao = new BugsDaoImpl(connection);
            Object maybeUserId = req.getSession().getAttribute("userId");
            if (maybeUserId == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            int userId = ((Integer) maybeUserId).intValue();
            dao.moveBug(Integer.valueOf(bugId), Integer.valueOf(newStateId));
            resp.sendRedirect("viewbugs");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
