package ru.voskhod.bugs.controller;

import freemarker.template.TemplateException;
import ru.voskhod.bugs.dao.BugsDao;
import ru.voskhod.bugs.dao.BugsDaoImpl;
import ru.voskhod.bugs.model.BugData;
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

public class ViewBugsServlet extends HttpServlet {

    private DataSource dataSource;

    public ViewBugsServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            BugsDao dao = new BugsDaoImpl(connection);
            BugData bugData = dao.getData();
            Map<String, Object> params = new HashMap<>();
            params.put("data", bugData);
            resp.setCharacterEncoding("UTF-8");
            TemplateUtil.render("bugs.ftl", params, resp.getWriter());
        } catch (TemplateException | SQLException e) {
            throw new ServletException(e);
        }
    }
}
