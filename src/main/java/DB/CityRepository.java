package DB;

import Model.City;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface CityRepository {
    /**
     * save cities from list to Database
     *
     * @param cityList list of cities
     */
    void saveCities(List<City> cityList);

    /**
     * Receiving list of all cities
     *
     * @return List of cities
     */
    List<City> getCityList();

    /**
     * Receiving list of cities sorted by Comparator
     * @param cityComparator comparator for sorting
     * @return sorted List of cities
     */
    List<City> getSortedCityList(Comparator<City> cityComparator);

    /**
     * Receiving regions with number of cities in it
     * @return Map of regions and count of cities in it
     */
    Map<String, Integer> getCityNumberByRegions();
}
