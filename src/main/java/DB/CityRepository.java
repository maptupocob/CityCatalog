package DB;

import Model.City;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class CityRepository implements Repository<City> {
    private static final CityRepository instance;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS City (" +
            " id INT AUTO_INCREMENT PRIMARY KEY, " +
            "    name VARCHAR(255) NOT NULL, " +
            "    region VARCHAR(255) NOT NULL, " +
            "    district VARCHAR(255) NOT NULL, " +
            "    population INT NOT NULL, " +
            "    foundation INT NOT NULL)";
    public static final String INS_CITY = "INSERT INTO City (name, region, district, population, foundation) VALUES (?, ?, ?, ?, ?)";
    public static final String SELECT_ALL = "SELECT * FROM City";
    private static final String GROUP_REGION = "SELECT region, count(name) as count FROM City\n" +
            "GROUP BY region\n" +
            "ORDER BY count desc, region;";
    private static final String SELECT_SAME_CITY = "SELECT * FROM City WHERE name = ? AND region = ?";
    private static String dbURL;
    private static String login;
    private static String password;

    static {
        instance = new CityRepository();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static CityRepository getInstance() {
        return instance;
    }

    private CityRepository() {
        Properties appProp = new Properties();
        try (InputStream is = CityRepository.class.getClassLoader().getResourceAsStream("app.properties")) {
            appProp.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dbURL = appProp.getProperty("prodDBURL");
        login = appProp.getProperty("login");
        password = appProp.getProperty("password");

        try (Connection connection = DriverManager.getConnection(dbURL, login, password);
             Statement st = connection.createStatement()) {
            st.execute(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveListToDB(List<City> cityList) {
        try (Connection connection = DriverManager.getConnection(dbURL, login, password);
             PreparedStatement ps = connection.prepareStatement(INS_CITY);
             PreparedStatement checkCity = connection.prepareStatement(SELECT_SAME_CITY)) {
            for (City city : cityList) {
                checkCity.setString(1, city.getName());
                checkCity.setString(2, city.getRegion());
                ResultSet rs = checkCity.executeQuery();
                if (rs.next()) continue;
                ps.setString(1, city.getName());
                ps.setString(2, city.getRegion());
                ps.setString(3, city.getDistrict());
                ps.setInt(4, city.getPopulation());
                ps.setInt(5, city.getFoundation());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<City> getAllList() {
        List<City> cityList = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(dbURL, login, password);
             PreparedStatement getAll = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = getAll.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                String region = rs.getString(3);
                String district = rs.getString(4);
                int population = rs.getInt(5);
                int foundation = rs.getInt(6);
                cityList.add(new City(name, region, district, population, foundation));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cityList;
    }



    @Override
    public List<City> getSortedList(Comparator<City> comparator) {
        List<City> cityList = getAllList();
        cityList.sort(comparator);
        return cityList;
    }

    @Override
    public Map<String, Integer> getGroupData() {
        Map<String, Integer> map = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(dbURL, login, password);
             PreparedStatement getRegions = connection.prepareStatement(GROUP_REGION)) {
            ResultSet rs = getRegions.executeQuery();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }
}
