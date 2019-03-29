import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.voskhod.bugs.dao.BugsDao;
import ru.voskhod.bugs.dao.BugsDaoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TestBugsDaoImpl {

    private Connection connection;
    private BugsDao dao;

    @Before
    public void init() throws SQLException{
        connection = DriverManager.getConnection("jdbc:h2:mem:");
        dao = new BugsDaoImpl(connection);

        dao.createSchema();
        Statement st = connection.createStatement();
        st.execute(
                "INSERT INTO states (ID, name, order_num, is_default)" +
                "VALUES (1, 'Новый1', 1, true)");
        st.execute(
                "INSERT INTO states (ID, name, order_num, is_default)" +
                        "VALUES (2, 'В разработке', 2, false)");
        st.execute(
                "INSERT INTO states (ID, name, order_num, is_default)" +
                        "VALUES (3, 'В тестировании', 3, false)");
        st.execute(
                "INSERT INTO states (ID, name, order_num, is_default)" +
                        "VALUES (4, 'Закрыт', 4, false)");
        st.execute(
                "INSERT INTO transitions (state_from, state_to, name, order_num)" +
                "VALUES (1, 2, 'В разработку', 1)");
        st.execute(
                "INSERT INTO transitions (state_from, state_to, name, order_num)" +
                        "VALUES (2, 3, 'В тестирование', 1)");
        st.execute(
                "INSERT INTO transitions (state_from, state_to, name, order_num)" +
                        "VALUES (3, 2, 'На доработку', 1)");
        st.execute(
                "INSERT INTO transitions (state_from, state_to, name, order_num)" +
                        "VALUES (3, 4, 'Закрыть', 2)");
    }


    @Test
    public void createSchema() throws Exception {
        ResultSet tables = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
        ArrayList<String> tablesList = new ArrayList<>();
        while (tables.next()){
            tablesList.add(tables.getString("TABLE_NAME"));
        }
        Assert.assertEquals(tablesList, Arrays.asList("BUGS", "STATES", "TRANSITIONS", "USERS"));
    }

    @Test
    public void addBug() throws Exception {

    }

    @Test
    public void moveBug() throws Exception {

    }

    @Test
    public void login() throws Exception {

    }

    @Test
    public void getData() throws Exception {

    }
}
