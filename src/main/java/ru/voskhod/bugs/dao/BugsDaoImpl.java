package ru.voskhod.bugs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.voskhod.bugs.model.Bug;
import ru.voskhod.bugs.model.BugButton;
import ru.voskhod.bugs.model.BugData;
import ru.voskhod.bugs.model.BugState;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class BugsDaoImpl implements BugsDao {

    private final Logger logger = LoggerFactory.getLogger(BugsDaoImpl.class);

    private Connection connection;

    public BugsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createSchema() {
        try {
            try (Statement st = connection.createStatement()) {
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
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    @Override
    public int addBug(String shortText, String fullText, int userId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT  INTO bugs(" +
                "short_text ," +
                "full_text ," +
                "state_id ," +
                "creator_id ," +
                "created," +
                ") values (?) ")) {
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
        List<BugState> states = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT ID, name, order_num FROM states ORDER BY order_num")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString(2);
                    int order_num = rs.getInt("order_num");
                    states.add(new BugState(id, name, new ArrayList<>(), new ArrayList<>()));
                }
            }

        }
        Map<Integer, List<BugButton>> buttonsByState = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT state_from, state_to, name, order_num FROM transitions ORDER BY order_num")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int state_to  = rs.getInt(2);
                    int state_from = rs.getInt(1);
                    String name = rs.getString("name");
                    List<BugButton> state_from_bug =  buttonsByState.computeIfAbsent(state_from, k -> new ArrayList<>() );
                    state_from_bug.add(new BugButton(name, state_to));
                }

            }
        }
        Map<Integer, List<Bug>> bugsByState = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT ID, short_text, state_id FROM bugs ORDER BY ID")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String short_text = rs.getString(2);
                    int state_id = rs.getInt(3);
                    List<Bug> bugsInState = bugsByState.computeIfAbsent(state_id, k -> new ArrayList<>());
                    bugsInState.add(new Bug(id, short_text));
                }

            }
        }

        for (BugState state : states) {
            List<Bug> bugs = bugsByState.getOrDefault(state.getId(), Collections.emptyList());
            state.getBugs().addAll(bugs);
            List<BugButton> buttons = buttonsByState.getOrDefault(state.getId(),Collections.emptyList());
            state.getButtons().addAll(buttons);
        }
        return new BugData(states);
    }
}
