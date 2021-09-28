package Controller;

import DB.RepoImpl;
import DB.CityRepository;
import Model.City;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        saveCitiesFromFile();
        CityRepository repo = RepoImpl.getInstance();
        while (true) {
            System.out.println("1) Список городов");
            System.out.println("2) Список городов по имени в обратном порядке");
            System.out.println("3) Список городов по федеральному округу и имени в обратном опрядке");
            System.out.println("4) Город с наибольшим населением");
            System.out.println("5) Количество городов по регионам");
            System.out.println("10) Выход");
            Scanner console = new Scanner(System.in);
            int choice = console.nextInt();
            if (choice == 1) {
                List<City> cityListFromDB = repo.getCityList();
                cityListFromDB.forEach(System.out::println);
                System.out.println();
            } else if (choice == 2) {
                repo.getSortedCityList((c1, c2) -> c2.getName().toUpperCase(Locale.ROOT).compareTo(c1.getName().toUpperCase(Locale.ROOT))).forEach(System.out::println);
                System.out.println();
            } else if (choice == 3) {
                repo.getSortedCityList(((c1, c2) -> {
                    int comp = c2.getDistrict().toUpperCase(Locale.ROOT).compareTo(c1.getDistrict().toUpperCase(Locale.ROOT));
                    if (comp == 0) {
                        comp = c2.getName().toUpperCase(Locale.ROOT).compareTo(c1.getName().toUpperCase(Locale.ROOT));
                    }
                    return comp;
                })).forEach(System.out::println);
                System.out.println();
            } else if (choice == 4) {
                City[] cityArray = repo.getCityList().toArray(new City[0]);
                int maxInd = 0;
                for (int j = 1; j < cityArray.length; j++) {
                    if (cityArray[j].getPopulation() > cityArray[maxInd].getPopulation()) {
                        maxInd = j;
                    }
                }
                System.out.printf("[%d] = %d%n%n", maxInd, cityArray[maxInd].getPopulation());

            } else if (choice == 5) {
                Map<String, Integer> regions = repo.getCityNumberByRegions();
                regions.forEach((s, i) -> System.out.println(s + " " + i));
            } else if (choice == 10) {
                System.exit(0);
            }
        }
    }

    private static void saveCitiesFromFile() {
        try {
            Scanner sc = new Scanner(Main.class.getClassLoader().getResourceAsStream("City.txt"));
            List<City> cityList = new LinkedList<>();
            int i = 0;
            while (sc.hasNext()) {
                i++;
                String line = sc.nextLine();
                String[] attrs = line.split(";");
                //System.out.println(line);
                if (attrs.length != 6) {
                    throw new RuntimeException("Incorrect city attribute count in line " + i);
                }
                String name = attrs[1];
                String region = attrs[2];
                String district = attrs[3];
                int population = Integer.parseInt(attrs[4]);
                int foundation = Integer.parseInt(attrs[5]);
                City city = new City(name, region, district, population, foundation);
                cityList.add(city);
            }
            RepoImpl.getInstance().saveCities(cityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
