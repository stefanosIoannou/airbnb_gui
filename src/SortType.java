package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The sorting type options available for the user to choose
 *
 * @author Stefanos Ioannou 
 * @version 25.03.2020
 */
public enum SortType {
    // Define the sorting types available in the list window
    ASCENDING("Ascending"){
        /**
         * Sort list of entries in ascending order.
         * @param list The list to be ordered
         * @return A sorted list in ascending order
         */
        @Override
        public List<AirbnbListing> typeSort(List<Entry> list) {
            if(list.get(0).isString()){
                Collections.sort(list, Entry.sortString);
            }
            else {
                Collections.sort(list);
            }
            return getPropertiesOf(list);
        }
    },
    DESCENDING("Descending"){
        /**
         * Sort a list of entries in descending order
         * @param list The list to be ordered
         * @return A sorted list in descending order
         */
        @Override
        public List<AirbnbListing> typeSort(List<Entry> list) {
            if(list.get(0).isString()){
                Collections.sort(list,Collections.reverseOrder(Entry.sortString));
            }
            else {
                Collections.sort(list, Collections.reverseOrder());
            }
            return getPropertiesOf(list);
        }
    };

    // The string representation of the sorting types
    private String typeChosen;

    /**
     * Constructor of the sortType enum
     * @param typeChosen The sorting type options the available to the user
     */
    SortType(String typeChosen) {
        this.typeChosen = typeChosen;
    }


    /**
     * Return the type chosen by the user
     * @return The sorting type chosen by the user
     */
    @Override
    public String toString(){
        return typeChosen;
    }

    /**
     * Sort the listings in the type specified by the user
     * @param list The list to be ordered
     * @return A sorted list of entries
     */
    public abstract List<AirbnbListing> typeSort(List<Entry> list);

    /**
     * Return the a list of properties from a list of entries
     * @param list A list of entries having src.AirbnbListing as value
     * @return A list of properties
     */
    private static List<AirbnbListing> getPropertiesOf(List<Entry> list){
        List<AirbnbListing> propertyList = new ArrayList<>();
        for(Entry entry:list){
            propertyList.add(entry.getValue());
        }
        return propertyList;
    }

}
