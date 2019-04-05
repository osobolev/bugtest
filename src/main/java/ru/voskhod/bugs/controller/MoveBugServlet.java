package ru.voskhod.bugs.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

public class MoveBugServlet extends HttpServlet {

    private DataSource dataSource;

    public MoveBugServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bugId = req.getParameter("bugId");
        String newStateId = req.getParameter("newStateId");
        // todo: доделать переход бага в новое состояние
        System.out.println(bugId);
        System.out.println(newStateId);
        resp.sendRedirect("viewbugs");
    }
}
