package Services;

import DB.CityRepository;
import DB.Repository;
import Model.City;

import java.util.*;

public class CityService implements Service<City> {

    private final Repository<City> repo;
    private static final CityService instance;

    static {
        instance = new CityService(CityRepository.getInstance());
    }

    public static CityService getInstance() {
        return instance;
    }

    public CityService(Repository<City> repo) {
        this.repo = repo;
    }

    @Override
    public void saveFromFile() {
        try {
            Scanner sc = new Scanner(Objects.requireNonNull(CityService.class.getClassLoader().getResourceAsStream("City.txt"), "Can not find city file"));
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
            CityRepository.getInstance().saveListToDB(cityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printAll() {
        List<City> cityListFromDB = repo.getAllList();
        cityListFromDB.forEach(System.out::println);
        System.out.println();
    }

    @Override
    public void printAllOrdered(Comparator<City> comp) {
        repo.getSortedList(comp).forEach(System.out::println);
        System.out.println();
    }

    @Override
    public void printGroup() {
        Map<String, Integer> regions = repo.getGroupData();
        regions.forEach((s, i) -> System.out.println(s + " " + i));
    }

    @Override
    public void printIndexOfMaxValue() {
        City[] cityArray = repo.getAllList().toArray(new City[0]);
        int maxInd = 0;
        for (int j = 1; j < cityArray.length; j++) {
            if (cityArray[j].getPopulation() > cityArray[maxInd].getPopulation()) {
                maxInd = j;
            }
        }
        System.out.printf("[%d] = %d%n%n", maxInd, cityArray[maxInd].getPopulation());
    }
}
