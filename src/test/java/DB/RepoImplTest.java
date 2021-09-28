package DB;

import Controller.Main;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.*;

public class RepoImplTest {
    static Connection connection;
    static PreparedStatement statement;
    static Properties appProp;
    public static final String INS_CITY = "INSERT INTO City (name, region, district, population, foundation) VALUES (?, ?, ?, ?, ?)";

    @BeforeClass
    public static void before(){
        appProp = new Properties();
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("app.properties")) {
            appProp.load(is);
            Class.forName(appProp.getProperty("SQLClassName"));
            connection = DriverManager.getConnection(appProp.getProperty("testDBURL"), appProp);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void after(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void getInstance() {
    }

    @org.junit.Test
    public void saveCities() {
        try {
            statement = connection.prepareStatement(INS_CITY);
            statement.setString(1, "TestCity");
            statement.setString(2, "TestRegion");
            statement.setString(3, "TestDistrict");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void getCityList() {
    }

    @org.junit.Test
    public void getSortedCityList() {
    }

    @org.junit.Test
    public void getCityNumberByRegions() {
    }
}