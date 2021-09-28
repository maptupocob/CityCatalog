package DB;

import Model.City;

import java.sql.*;
import java.util.*;

public class DBHelper {
    final private static String dbURL = "jdbc:mysql://localhost:3306/Test";
    final private static String login = "root";
    final private static String password = "!Q2w3e4r";

    static {
        try (Connection connection = DriverManager.getConnection(dbURL, login, password);
             Statement st = connection.createStatement()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            st.execute("CREATE TABLE IF NOT EXISTS City (" +
                    " id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "    name VARCHAR(255) NOT NULL, " +
                    "    region VARCHAR(255) NOT NULL, " +
                    "    district VARCHAR(255) NOT NULL, " +
                    "    population INT NOT NULL, " +
                    "    foundation INT NOT NULL)");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveCities(List<City> cityList) {
        try (Connection connection = DriverManager.getConnection(dbURL, login, password);
             PreparedStatement ps = connection.prepareStatement("INSERT INTO City (name, region, district, population, foundation) VALUES (?, ?, ?, ?, ?)");
             PreparedStatement checkCity = connection.prepareStatement("SELECT * FROM City WHERE name = ? AND region = ?")) {
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
        return false;
    }

    public static List<City> getCityList() {
        List<City> cityList = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(dbURL, login, password);
             PreparedStatement getAll = connection.prepareStatement("SELECT * FROM City")) {
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

    public static List<City> getSortedCityList(Comparator<City> cityComparator) {
        List<City> cityList = getCityList();
        Collections.sort(cityList, cityComparator);
        return cityList;
    }

    public static Map<String, Integer> getCityNumberByRegions() {
        Map<String, Integer> map = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(dbURL, login, password);
             PreparedStatement getRegions = connection.prepareStatement("SELECT region, count(name) as count FROM City\n" +
                     "GROUP BY region\n" +
                     "ORDER BY count desc, region;")) {
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
