package ru.voskhod.bugs.dao;

import ru.voskhod.bugs.model.BugData;

import java.sql.SQLException;
import java.util.OptionalInt;

public interface BugsDao {

    void createSchema();

    int addBug(String shortText, String fullText, int userId) throws SQLException;

    void moveBug(int bugId, int newStateId) throws SQLException;

    OptionalInt login(String login, String password) throws SQLException;

    BugData getData() throws SQLException;
}
