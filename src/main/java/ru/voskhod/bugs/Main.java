package ru.voskhod.bugs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.voskhod.bugs.controller.*;
import ru.voskhod.bugs.dao.BugsDaoImpl;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.EnumSet;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(handler);
        handler.setResourceBase("webroot");
        handler.addServlet(DefaultServlet.class, "/*");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:~/bugsdb");
        DataSource dataSource = new HikariDataSource(config);
        try (Connection connection = dataSource.getConnection()) {
            new BugsDaoImpl(connection).createSchema();
        }
        handler.addServlet(new ServletHolder(new ViewBugsServlet(dataSource)), "/app/viewbugs");
        handler.addServlet(new ServletHolder(new AddBugServlet(dataSource)), "/app/addbug");
        handler.addServlet(new ServletHolder(new MoveBugServlet(dataSource)), "/app/movebug");
        handler.addServlet(new ServletHolder(new LoginServlet(dataSource)), "/login");
        handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
        handler.addFilter(LoginFilter.class, "/app/*", EnumSet.of(DispatcherType.REQUEST));

        server.start();
    }
}
