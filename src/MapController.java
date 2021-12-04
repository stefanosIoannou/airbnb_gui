package src;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.*;


/**
 * Class controlling the map in the map panel. The class colours the map according
 * to the number of occurrences in the listings, sets options to the sorting drop downs ,
 * assigns each borough name to an id which is linked to the borough shape in the fxml and
 * creates the tooltips
 *
 * @author Stefanos Ioannou 
 * @version 13.03.2020
 */
public class MapController implements PropertyManipulation, MapColouring {

    // List of shapes in the map
    private static List<SVGPath> shapeList;

    // A map of borough ids and the full name of the borough
    private final HashMap<String, String> BOROUGH_IDS;

    // The list of boroughs full names
    private final List<String> BOROUGH_NAME_LIST;

    // AirbnbListings filters by the price drop downs
    private static List<AirbnbListing> listings;

    // The group containing the svg paths for the shape
    @FXML
    private Group map;

    /**
     * The constructor of the src.MapController class
     */
    public MapController(){
        shapeList = new ArrayList<>();

        BOROUGH_IDS = new HashMap<>();

        // Create a list of the names of the boroughs
        BOROUGH_NAME_LIST = new ArrayList<>();

    }

    /**
     * Initialize the src.MapPanel.
     * Assign ids to each name, colour the map and set up the drop down options
     */
    @FXML
    void initialize() {
        listings = getPropertiesFromRange(Panel.getCurrentFromValue(),Panel.getCurrentToValue());
        getMapBoroughs();
        setBoroughID();
        colourBoroughByPopularity();
        setOnClickEvent();
    }

    /**
     * Refresh and recolour the map
     */
    public void refreshMap(){
        listings.clear();
        listings = getPropertiesFromRange(Panel.getCurrentFromValue(),Panel.getCurrentToValue());
        colourBoroughByPopularity();
    }

    /**
     * Set on click event to all the boroughs in the map
     * Clicking a borough opens a new window with a list
     * of the available properties in the borough
     */
    private void setOnClickEvent() {
        for(SVGPath borough: shapeList){
            borough.setOnMouseClicked(e -> {
                try {
                    ListWindow listWindow = new ListWindow(BOROUGH_IDS.get(borough.getId()));
                    listWindow.start(new Stage());
                } catch (Exception ex) {
                    return;
                }
            });
        }
    }

    /**
     * Load the boroughs from the map in a list
     */
    private void getMapBoroughs() {
        for (Node node : map.getChildren()) {
            if (node instanceof SVGPath) {
                SVGPath newShape = (SVGPath) node;
                shapeList.add(newShape);
                setBoroughID();
                createToolTips(newShape);
            }
        }
    }

    /**
     * Associate the boroughID of the FXML to the Neighbourhood
     */
    private void setBoroughID() {
        BOROUGH_NAME_LIST.add("City of London");
        BOROUGH_NAME_LIST.add("Barking and Dagenham");
        BOROUGH_NAME_LIST.add("Barnet");
        BOROUGH_NAME_LIST.add("Bexley");
        BOROUGH_NAME_LIST.add("Brent");
        BOROUGH_NAME_LIST.add("Bromley");
        BOROUGH_NAME_LIST.add("Camden");
        BOROUGH_NAME_LIST.add("Croydon");
        BOROUGH_NAME_LIST.add("Ealing");
        BOROUGH_NAME_LIST.add("Enfield");
        BOROUGH_NAME_LIST.add("Greenwich");
        BOROUGH_NAME_LIST.add("Hackney");
        BOROUGH_NAME_LIST.add("Hammersmith and Fulham");
        BOROUGH_NAME_LIST.add("Haringey");
        BOROUGH_NAME_LIST.add("Harrow");
        BOROUGH_NAME_LIST.add("Havering");
        BOROUGH_NAME_LIST.add("Hillingdon");
        BOROUGH_NAME_LIST.add("Hounslow");
        BOROUGH_NAME_LIST.add("Islington");
        BOROUGH_NAME_LIST.add("Kensington and Chelsea");
        BOROUGH_NAME_LIST.add("Kingston upon Thames");
        BOROUGH_NAME_LIST.add("Lambeth");
        BOROUGH_NAME_LIST.add("Lewisham");
        BOROUGH_NAME_LIST.add("Merton");
        BOROUGH_NAME_LIST.add("Newham");
        BOROUGH_NAME_LIST.add("Redbridge");
        BOROUGH_NAME_LIST.add("Richmond upon Thames");
        BOROUGH_NAME_LIST.add("Southwark");
        BOROUGH_NAME_LIST.add("Sutton");
        BOROUGH_NAME_LIST.add("Tower Hamlets");
        BOROUGH_NAME_LIST.add("Waltham Forest");
        BOROUGH_NAME_LIST.add("Wandsworth");
        BOROUGH_NAME_LIST.add("Westminster");

        for (String borough : BOROUGH_NAME_LIST) {
            BOROUGH_IDS.put(convertBoroughID(borough), borough);
        }
    }

    /**
     * Generate ids for the FXML using the names of the boroughs
     *
     * @param neighbourhood The neighbourhood from the AirBNB listings
     * @return The id for the borough in the FXML
     */
    private static String convertBoroughID(String neighbourhood) {
        String[] words = neighbourhood.split(" ");
        return words[0].toLowerCase() + "_id";
    }

    /**
     * Create the tooltips that are going to show the name of the borough
     * @param borough The Shape the tool tip is going to be added to
     */
    private void createToolTips(Shape borough) {
        Tooltip t = new Tooltip(BOROUGH_IDS.get(borough.getId()));
        Tooltip.install(borough, t);
    }

    /**
     * Colour the borough based on the number of occurrences in the airbnb listings.
     * The more occurrences a borough has in the listings, the darker the colour will be
     * Colour is proportional to the number of occurrences of all boroughs in the file
     */
    private void colourBoroughByPopularity() {
        HashMap<String,Double> percentages = getPercentageList(getMapOccurrences());
        for(String borough:BOROUGH_NAME_LIST){
            double fraction = percentages.get(borough);
            colorBorough(findSVGPath(borough),fraction);
        }
    }

    /**
     * Return a list showing only the neighborhoods in the airbnb listings
     * @param listings Airbnb listings
     * @return A list of neighbourhoods
     */
    private List<String> getAllNeighbourhoods(List<AirbnbListing> listings){
        List<String> neighborhoods = new ArrayList<>();
        for (AirbnbListing listing : listings) {
            neighborhoods.add(listing.getNeighbourhood());
        }
        return neighborhoods;
    }

    /**
     * Return an unordered map list of the neighbourhoods with the number of occurrences of each neighbourhood
     * @return Unordered map list of neighborhoods and their occurrences
     */
    private HashMap<String, Integer> getMapOccurrences(){
        HashMap<String, Integer> occurrences = new HashMap<>();

        // Find the number of occurrences of each borough in the list
        for (String borough : BOROUGH_NAME_LIST) {
            occurrences.put(borough, Collections.frequency(getAllNeighbourhoods(listings), borough));
        }

        return occurrences;
    }

    /**
     * Return the SVGpath Object based on the name of the Neighborhood
     * @param borough Name of the neighbourhood. This is converted into an id
     * @return SVGpath
     */
    private SVGPath findSVGPath(String borough){
        for (SVGPath shape : shapeList) {
            String borough_id = convertBoroughID(borough);
            if (shape.getId().equals(borough_id)) {
                return shape;
            }
        }
        return null;
    }

    /**
     * Get a HashMap of the boroughs plus the percentage of occurrences in the file
     * @param neighbourhoods Map of neighborhoods and occurrences
     * @return map of neighborhoods and percentages or null
     */
    private HashMap<String,Double> getPercentageList(HashMap<String,Integer> neighbourhoods) {
        // Calculate denominator
        double denominator = listings.size();

        HashMap<String,Double> percentages = new HashMap<>();
        for(String borough: BOROUGH_NAME_LIST){
            double numerator = neighbourhoods.get(borough);

            // Ensure no division is done by 0
            double fraction;
            if(denominator == 0 ){
                fraction = 0;
            }
            else{
                fraction = numerator/denominator;
            }
            percentages.put(borough, fraction);
        }

        return percentages;
    }

}
