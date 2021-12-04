package src;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */ 

public class AirbnbListing {
    /**
     * The id and name of the individual property
     */
    private final String id;
    private final String name;
    /**
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private final String host_id;
    private final String host_name;

    /**
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private final String neighbourhood;

    /**
     * The location on a map where the property is situated.
     */
    private final double latitude;
    private final double longitude;

    /**
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private final String room_type;

    /**
     * The price per night's stay
     */
    private final int price;

    /**
     * The minimum number of nights the listed property must be booked for.
     */
    private final int minimumNights;
    private final int numberOfReviews;

    /**
     * The date of the last review, but as a String
     */
    private final String lastReview;
    private final double reviewsPerMonth;

    /**
     * The total number of listings the host holds across AirBnB
     */
    private final int calculatedHostListingsCount;
    /**
     * The total number of days in the year that the property is available for
     */
    private final int availability365;


    public AirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
    }

    public String getName() {
        return name;
    }

    public String getHost_name() {
        return host_name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getRoom_type() {
        return room_type;
    }

    public int getPrice() {
        return price;
    }

    public int getMinimumNights() {
        return minimumNights;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public int getAvailability365() {
        return availability365;
    }
    
    /**
     * Method to display a property listing used in the src.PropertyController class.
     * @author Charles
     */
    public String display()
    {
        return "Property Name: " + name +
                "\n" + "Room Type: " + room_type +
                "\n" + "Host Name(Host ID): " + host_name + "(" + host_id + ")" +
                "\n" + "Properties Managed by Host: " + calculatedHostListingsCount +
                "\n" + "Neighborhood: " + neighbourhood +
                "\n" + "Latitude - Longitude: " + latitude + "-" + longitude +
                "\n" + "Price: " + "£" + price +
                "\n" + "Minimum Number of Nights: " + minimumNights +
                "\n" + "Number of Reviews: " + numberOfReviews +
                "\n" + "Last Review: " + lastReview +
                "\n" + "Reviews per Month: " + reviewsPerMonth +
                "\n" + "Availability: " + availability365;
    }

    @Override
    public String toString() {
        return "src.AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }

    /**
     * Create the listings to be displayed in the window
     * @return the lists
     */
    public Node createListing() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Listing.fxml"));
        PropertyController controller = new PropertyController(this);
        loader.setController(controller); //define the controller
        return loader.load();
    }
}
