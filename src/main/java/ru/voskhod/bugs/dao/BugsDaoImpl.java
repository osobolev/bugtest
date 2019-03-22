package ru.voskhod.bugs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.voskhod.bugs.model.BugData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.OptionalInt;

public class BugsDaoImpl implements BugsDao {

    private final Logger logger = LoggerFactory.getLogger(BugsDaoImpl.class);

    private DataSource dataSource;

    public BugsDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createSchema() {
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            st.execute(
                "CREATE TABLE ..."
            );
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    @Override
    public int addBug(String shortText, String fullText, int userId) throws SQLException {
        return 0; // todo
    }

    @Override
    public void moveBug(int bugId, int newStateId) throws SQLException {
        // todo
    }

    @Override
    public OptionalInt login(String login, String password) throws SQLException {
        return null; // todo
    }

    @Override
    public BugData getData() throws SQLException {
        return null; // todo
    }
}
