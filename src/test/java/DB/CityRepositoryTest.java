package DB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;

public class CityRepositoryTest {
    static Connection connection;
    static PreparedStatement statement;
    static Properties appProp;

    @Before
    public void setUp() throws Exception {
        appProp = new Properties();
        try (InputStream is = CityRepositoryTest.class.getClassLoader().getResourceAsStream("app.properties")) {
            appProp.load(is);
            Class.forName(appProp.getProperty("SQLClassName"));
            connection = DriverManager.getConnection(appProp.getProperty("testDBURL"), appProp);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }



    @Test
    public void saveListToDB() {

    }

    @Test
    public void getAllList() {
    }

    @Test
    public void getSortedList() {
    }

    @Test
    public void getGroupData() {
    }
}