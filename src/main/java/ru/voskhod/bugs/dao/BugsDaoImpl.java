package ru.voskhod.bugs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.voskhod.bugs.model.BugData;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                "CREATE TABLE users(" +
                        "ID int," +
                        "login varchar(50)," +
                        "pass_hash varchar(64)," +
                        "PRIMARY KEY(ID)" +
                        ");"
            );
            st.execute(
                    "CREATE TABLE states(" +
                            "ID int," +
                            "name varchar(50)," +
                            "order_num int," +
                            "is_default boolean," +
                            "PRIMARY KEY(ID)" +
                            ");"
            );
            st.execute(
                    "CREATE TABLE transitions(" +
                            "state_from int," +
                            "state_to int," +
                            "name varchar(50)," +
                            "order_num int," +
                            "PRIMARY KEY(state_from , state_to)" +
                            ");"
            );
            st.execute(
                    "CREATE TABLE bugs(" +
                            "ID int," +
                            "short_text varchar(100)," +
                            "full_text varchar (1000)," +
                            "state_id int," +
                            "creator_id int," +
                            "created timestamp," +
                            "PRIMARY KEY(ID)" +
                            ");"
            );
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    @Override
    public int addBug(String shortText, String fullText, int userId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            st.execute(
                    "INSERT INTO users(" +
                            "ID int," +
                            "login varchar(50)," +
                            "pass_hash varchar(64)," +
                            "PRIMARY KEY(ID)" +
                            ");"
            );
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return 0; //id сгенерированной записи
    }

    @Override
    public void moveBug(int bugId, int newStateId) throws SQLException {
        // todo
    }

    @Override
    public OptionalInt login(String login, String password) throws SQLException {
        MessageDigest sha256;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
        sha256.update((login + password).getBytes(StandardCharsets.UTF_8));
        byte[] digest = sha256.digest();
        String hash = DatatypeConverter.printHexBinary(digest);
        return OptionalInt.empty(); // todo
    }

    @Override
    public BugData getData() throws SQLException {
        return null; // todo
    }
}
