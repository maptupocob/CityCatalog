package Services;

import java.util.Comparator;

public interface Service<T> {
    /**
     * save all entries from file to DB
     */
    void saveFromFile();

    /**
     * print all entries from DB table
     */
    void printAll();

    /**
     * print all entries from DB table ordered by comp
     * @param comp comparator for sorting
     */
    void printAllOrdered(Comparator<T> comp);

    /**
     * print grouped data
     */
    void printGroup();

    /**
     * print index of entry with max value and value
     */
    void printIndexOfMaxValue();
}
