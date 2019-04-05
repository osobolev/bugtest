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
            String bugIdStr = req.getParameter("bugId");
            String newStateIdStr = req.getParameter("newStateId");

            BugsDao dao = new BugsDaoImpl(connection);
            int bugId = Integer.parseInt(bugIdStr);
            int newStateId = Integer.parseInt(newStateIdStr);
            if (!dao.isValidMove(bugId, newStateId)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            dao.moveBug(bugId, newStateId);
            resp.sendRedirect("viewbugs");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
