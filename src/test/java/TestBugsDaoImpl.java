import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.voskhod.bugs.dao.BugsDao;
import ru.voskhod.bugs.dao.BugsDaoImpl;
import ru.voskhod.bugs.model.BugData;
import ru.voskhod.bugs.view.MainView;
import ru.voskhod.bugs.view.MainViewImpl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestBugsDaoImpl {

    private Connection connection;
    private BugsDao dao;

    @Before
    public void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");
        dao = new BugsDaoImpl(connection);

        dao.createSchema();
    }


    @Test
    public void createSchema() throws Exception {
        ResultSet tables = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
        ArrayList<String> tablesList = new ArrayList<>();
        while (tables.next()) {
            tablesList.add(tables.getString("TABLE_NAME"));
        }
        Assert.assertEquals(tablesList, Arrays.asList("BUGS", "STATES", "TRANSITIONS", "USERS"));
    }

    @Test
    public void addBug() throws Exception {
        dao.addBug("Bug1", "Here's new bug", 1);
        Statement addedBug  = connection.createStatement();
        ResultSet rs = addedBug.executeQuery(
                "SELECT id, short_text, full_text ,state_id ,creator_id ,created" +
                " FROM bugs WHERE id = 1");
        StringBuilder str = new StringBuilder();
        while (rs.next()){
            str.append(rs.getString("id") + rs.getString("short_text") + rs.getString("full_text"));
        }
        Assert.assertEquals("1Bug1Here's new bug", str.toString());
        System.out.println(str.toString());
    }

    @Test
    public void moveBug() throws Exception {
        dao.addBug("Bug1", "Here's new bug", 1);
        dao.moveBug(1, 2);

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT state_id FROM bugs WHERE id = 1");
        String str = new String();
        while (rs.next()){
          str = rs.getString("state_id");
        }
        System.out.println(str);
        Assert.assertEquals(str, "2");
    }

    @Test
    public void login() throws Exception {
        dao.login("admin", "password");
    }

    @Test
    public void getData() throws Exception {
        dao.getData();
    }

    @Test
    public void testView() throws Exception {
        dao.addBug("Bug1", "Here's new bug", 1);
        dao.addBug("Bug2", "Here's new bug", 1);
        BugData data = dao.getData();
        MainView view = new MainViewImpl();
        view.render(data, new PrintWriter(System.out));
    }

    @Test
    public void testHash() {
        String hash1 = BugsDaoImpl.getHash("test", "test123");
        System.out.println(hash1);
        String hash2 = BugsDaoImpl.getHash("dev", "dev123");
        System.out.println(hash2);
    }
}
