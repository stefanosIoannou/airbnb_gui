package src;

import java.util.ArrayList;
import java.util.List;

/**
 * The options the user can choose to sort by
 *
 * @author Stefanos Ioannou 
 * @version 25.03.2020
 */
public enum SortBy {
    // Define the columns the user can sort by
    PRICE("Price"){
        /**
         * Create a list of entries with price as key
         * @param properties The list of  properties
         * @return A list of entries with price as key
         */
        @Override
        public List<Entry> byColumn(List<AirbnbListing> properties) {
            ArrayList<Entry> unsorted = new ArrayList<>();
            for(AirbnbListing property:properties){
                unsorted.add(new Entry(property.getPrice(),property));
            }
            return unsorted;
        }
    },MIN_NIGHTS("Nights") {
        /**
         * Create a list of entries with minimum nights as key
         * @param properties The list of  properties
         * @return A list of entries with minimum nights as key
         */
        @Override
        public List<Entry> byColumn(List<AirbnbListing> properties) {
            ArrayList<Entry> unsorted = new ArrayList<>();
            for(AirbnbListing property:properties){
                unsorted.add(new Entry(property.getMinimumNights(),property));
            }
            return unsorted;
        }
    },HOST("Host") {
        /**
         * Create a list of entries with the host as key
         * @param properties The list of  properties
         * @return A list of entries with the host as key
         */
        @Override
        public List<Entry> byColumn(List<AirbnbListing> properties) {
            ArrayList<Entry> unsorted = new ArrayList<>();
            for(AirbnbListing property:properties){
                unsorted.add(new Entry(property.getHost_name(),property));
            }
            return unsorted;
        }
    },REVIEWS("Reviews") {
        /**
         * Create a list of entries with number of reviews as key
         * @param properties The list of  properties
         * @return A list of entries with number of reviews as key
         */
        @Override
        public List<Entry> byColumn(List<AirbnbListing> properties) {
            ArrayList<Entry> unsorted = new ArrayList<>();
            for(AirbnbListing property:properties){
                unsorted.add(new Entry(property.getNumberOfReviews(),property));
            }
            return unsorted;
        }
    };

    // The string representation of each option
    private final String columnChosen;

    /**
     * Constructor of the src.SortBy enum
     * @param columnChosen The columns the user can choose to sort by
     */
    SortBy(String columnChosen) {
        this.columnChosen = columnChosen;
    }

    /**
     * Return the string representation of each enum option
     * @return string representation of each option
     */
    @Override
    public String toString() {
        return columnChosen;
    }

    /**
     * Return a list of entries having the column value as key and the property object as value
     * @param properties The list of  properties
     * @return A list of entries
     */
    public abstract List<Entry> byColumn(List<AirbnbListing> properties);
}
