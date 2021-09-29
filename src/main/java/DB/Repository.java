package DB;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface Repository<T> {
    /**
     * save entities from list to Database
     *
     * @param cityList list of entities
     */
    void saveListToDB(List<T> cityList);

    /**
     * Receiving list of all entities
     *
     * @return List of entities
     */
    List<T> getAllList();

    /**
     * Receiving list of entities sorted by Comparator
     *
     * @param comparator comparator for sorting
     * @return sorted List of cities
     */
    List<T> getSortedList(Comparator<T> comparator);

    /**
     * Receiving group data with number of entities in it
     *
     * @return Map of groups and count of entities in it
     */
    Map<String, Integer> getGroupData();
}
