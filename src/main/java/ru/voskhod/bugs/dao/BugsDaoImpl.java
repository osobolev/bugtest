package ru.voskhod.bugs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.voskhod.bugs.model.BugData;

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

    private Connection connection;

    public BugsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createSchema() {
        try {
            Statement st = connection.createStatement();
            st.execute(
                "CREATE TABLE users (" +
                "  ID int PRIMARY KEY," +
                "  login varchar(50) not null," +
                "  pass_hash varchar(64)" +
                ")"
            );
            st.execute(
                "CREATE TABLE states (" +
                "  ID int PRIMARY KEY," +
                "  name varchar(50) not null," +
                "  order_num int not null," +
                "  is_default boolean not null" +
                ")"
            );
            st.execute(
                "CREATE TABLE transitions (" +
                "  state_from int not null REFERENCES states (ID)," +
                "  state_to int not null REFERENCES states (ID)," +
                "  name varchar(50) not null," +
                "  order_num int not null," +
                "  PRIMARY KEY (state_from , state_to)" +
                ")"
            );
            st.execute(
                "CREATE TABLE bugs (" +
                "  ID int PRIMARY KEY," +
                "  short_text varchar(100) not null," +
                "  full_text varchar (1000) not null," +
                "  state_id int not null REFERENCES states (ID)," +
                "  creator_id int not null REFERENCES users (ID)," +
                "  created timestamp not null" +
                ")"
            );
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    @Override
    public int addBug(String shortText, String fullText, int userId) throws SQLException {
        Statement st = connection.createStatement();
        st.execute(
                "INSERT INTO users(" +
                        "ID int," +
                        "login varchar(50)," +
                        "pass_hash varchar(64)," +
                        "PRIMARY KEY(ID)" +
                        ");"
        );
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
