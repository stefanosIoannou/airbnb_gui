package src;

import src.AirbnbListing;
import src.GUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface enabling the panels to handle processes based on the properties filtered by the
 * from to prices chosen by the user
 *
 * @author Stefanos Ioannou , Alexandre Chouman 
 * @version 25.03.2020
 */
public interface PropertyManipulation {

    /**
     * Returns the a list pf properties that are in the range selected by the used
     * @param fromValue Lower Boundary of price
     * @param toValue Upper Boundary of price
     * @return The listings in the selected range
     * @throws IllegalArgumentException When a negative value is chosen
     */
    default List<AirbnbListing> getPropertiesFromRange(int fromValue, int toValue){
        if(fromValue < 0 || toValue < 0){
            throw new IllegalArgumentException("use positive values");
        }
        ArrayList<AirbnbListing> ListingsList = new ArrayList<>();
        for(AirbnbListing listing : GUI.getListingsFromFile()){
            if(listing.getPrice() >= fromValue && listing.getPrice() <= toValue){
                ListingsList.add(listing);
            }
        }
        return ListingsList;
    }
}
