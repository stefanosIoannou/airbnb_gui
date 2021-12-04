package src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import java.util.List;
import java.util.ArrayList;
/**
    * The Stats panel creates and builds a pane made of one grid pane
    * containing four border panes. It contains methods for handeling 
    * when the buttons are pressed, overrides the refresh and getPane 
    * methods, as well as the methods for creating the statistics 
    * displayed.
    * @author Charles Salser , Aaron Ram 
    * @version 27.03.2020
    */
public class StatisticsPanel extends Panel implements PropertyManipulation, Refreshable
{
    private GridPane statPanel;
    private BorderPane q1;
    private BorderPane q2;
    private BorderPane q3;
    private BorderPane q4;
    private Button q1LeftButton, q2LeftButton, q3LeftButton, q4LeftButton ;
    private Button q1RightButton, q2RightButton, q3RightButton, q4RightButton;
    private List<AirbnbListing> properties;
    private Statistic q1Stat, q2Stat, q3Stat, q4Stat;
    private ArrayList<Statistic> hiddenStats;

    private Label q1Label, q2Label, q3Label, q4Label;
    /**
    * Constructor of a stats panel
    */
    @Override
    public Pane getDisplay()
    {
        statPanel = new GridPane();
        statPanel.getStylesheets().add("style/statisticsPanel.css");
        
        q1 = new BorderPane();
        q2 = new BorderPane();
        q3 = new BorderPane();
        q4 = new BorderPane(); 
        
        q1Label = new Label();
        q2Label = new Label();
        q3Label = new Label();
        q4Label = new Label();

        hiddenStats = new ArrayList<Statistic>();
        refresh();

        //SET UP Q1 BORDERPANE
        q1LeftButton = new Button("<<");
        q1LeftButton.setDisable(false);
        q1LeftButton.setOnAction(this::q1PreviousStat);
        q1.setLeft(q1LeftButton);
        q1RightButton = new Button(">>");
        q1RightButton.setDisable(false);
        q1RightButton.setOnAction(this::q1NextStat);
        q1.setRight(q1RightButton);        
        q1.setCenter(q1Label);

        //SET UP Q2 BORDER PANE
        q2LeftButton = new Button("<<");
        q2LeftButton.setDisable(false);
        q2LeftButton.setOnAction(this::q2PreviousStat);
        q2.setLeft(q2LeftButton);        
        q2RightButton = new Button(">>");
        q2RightButton.setDisable(false);
        q2RightButton.setOnAction(this::q2NextStat);
        q2.setRight(q2RightButton);       
        q2.setCenter(q2Label);

        //SET UP Q3 BORDER PANE
        q3LeftButton = new Button("<<");
        q3LeftButton.setDisable(false);
        q3LeftButton.setOnAction(this::q3PreviousStat);
        q3.setLeft(q3LeftButton);        
        q3RightButton = new Button(">>");
        q3RightButton.setDisable(false);
        q3RightButton.setOnAction(this::q3NextStat);
        q3.setRight(q3RightButton);        
        q3.setCenter(q3Label);

        //SET UP Q4 BORDER PANE
        q4LeftButton = new Button("<<");
        q4LeftButton.setDisable(false);
        q4LeftButton.setOnAction(this::q4PreviousStat);
        q4.setLeft(q4LeftButton);        
        q4RightButton = new Button(">>");
        q4RightButton.setDisable(false);
        q4RightButton.setOnAction(this::q4NextStat);
        q4.setRight(q4RightButton);       
        q4.setCenter(q4Label);

        //SET UP GRIDPANE
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        statPanel.getColumnConstraints().addAll(column1,column2);        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        statPanel.getRowConstraints().addAll(row1,row2);

        statPanel.add(q1, 0, 0);
        statPanel.add(q2, 1, 0);
        statPanel.add(q3, 0, 1);
        statPanel.add(q4, 1, 1);
               
        return statPanel;
    }

    //Methods for Buttons
    /**
    * Moves the current statistic to the front of the list of not displayed stats
    * and displays the last one, or previous stat on q1
    */
    public void q1PreviousStat(ActionEvent e)
    {
        Statistic temp = q1Stat;
        q1Stat = hiddenStats.remove(hiddenStats.size()-1);
        hiddenStats.add(0, temp);
        q1Label.setText("" + q1Stat);
    }
    
    /**
    * Moves the current statistic to the end of the list of not displayed stats
    * and displays the next stat on q1 
    */
    public void q1NextStat(ActionEvent e)
    {
        Statistic temp = q1Stat;
        q1Stat = hiddenStats.remove(0);
        hiddenStats.add(hiddenStats.size(),temp);
        q1Label.setText("" + q1Stat);
    }
    
    /**
    * Moves the current statistic to the front of the list of not displayed stats
    * and displays the last one, or previous stat on q2
    */
    public void q2PreviousStat(ActionEvent e)
    {
        Statistic temp = q2Stat;
        q2Stat = hiddenStats.remove(hiddenStats.size()-1);
        hiddenStats.add(0, temp);
        q2Label.setText("" + q2Stat);
    }
    
    /**
    * Moves the current statistic to the end of the list of not displayed stats
    * and displays the next stat on q2 
    */
    public void q2NextStat(ActionEvent e)
    {
        Statistic temp = q2Stat;
        q2Stat = hiddenStats.remove(0);
        hiddenStats.add(hiddenStats.size(),temp);
        q2Label.setText("" + q2Stat);
    }
    
    /**
    * Moves the current statistic to the front of the list of not displayed stats
    * and displays the last one, or previous stat on q3
    */
    public void q3PreviousStat(ActionEvent e)
    {
        Statistic temp = q3Stat;
        q3Stat = hiddenStats.remove(hiddenStats.size()-1);
        hiddenStats.add(0, temp);
        q3Label.setText("" + q3Stat);
    }

    /**
    * Moves the current statistic to the end of the list of not displayed stats
    * and displays the next stat on q3
    */
    public void q3NextStat(ActionEvent e)
    {
        Statistic temp = q3Stat;
        q3Stat = hiddenStats.remove(0);
        hiddenStats.add(hiddenStats.size(),temp);
        q3Label.setText("" + q3Stat);
    }
    
    /**
    * Moves the current statistic to the front of the list of not displayed stats
    * and displays the last one, or previous stat on q4
    */
    public void q4PreviousStat(ActionEvent e)
    {
        Statistic temp = q1Stat;
        q1Stat = hiddenStats.remove(hiddenStats.size()-1);
        hiddenStats.add(0, temp);
        q4Label.setText("" + q1Stat);
    }

    /**
    * Moves the current statistic to the end of the list of not displayed stats
    * and displays the next stat on q4 
    */
    public void q4NextStat(ActionEvent e)
    {
        Statistic temp = q4Stat;
        q4Stat = hiddenStats.remove(0);
        hiddenStats.add(hiddenStats.size(),temp);
        q4Label.setText("" + q4Stat);
    }

    /**
    * Overrides the refresh method from the pane class. Updates the list of properties
    * and clears the arrayList of hidden stats. Recreates all stats ojects to account 
    * for the new values.
    */
    @Override 
    public void refresh()
    {
        properties = getPropertiesFromRange(Panel.getCurrentFromValue(), Panel.getCurrentToValue());
        hiddenStats.clear();

        q1Stat = new Statistic("Average Number of Reviews", String.valueOf(getAverageReviews()));
        q2Stat = new Statistic("Total Number of Available Properties", String.valueOf(getAllAvailableProperties()));
        q3Stat = new Statistic("Number of Homes and Apartments", String.valueOf(getAllHomesApartments()));
        q4Stat = new Statistic("Most Expensive Borough", getMostExpensive());

        hiddenStats.add(new Statistic("Average Price per Night", "£" + String.valueOf(getAveragePrice())));
        hiddenStats.add(new Statistic("Number of Private Rooms", String.valueOf(getAllPrivateRooms())));
        hiddenStats.add(new Statistic("Cheapest Borough", getLeastExpensive()));
        hiddenStats.add(new Statistic("Average Number of Minimum Nights", String.valueOf(averageMinNights())));

        q1Label.setText("" + q1Stat);
        q2Label.setText("" + q2Stat);
        q3Label.setText("" + q3Stat);
        q4Label.setText("" + q4Stat);
    }

    /**
     * Check the activity of the panel
     */
    @Override
    public void checkActivity() {
        isActive();
    }

    // Methods for calculating the statistics
    /**
     * A method for calculating the average number of reviews from the properties within the price range
     * @return averageReviews the average number of reviews
     */
    private int getAverageReviews()
    {
        int totalReviews = 0;
        int totalItems = properties.size();
        for (AirbnbListing listing : properties)
        {
            totalReviews += listing.getNumberOfReviews();
        }
        int averageReviews = totalReviews / totalItems;
        return averageReviews;
    }

    /**
     * A method to return how many properties there are within the price range
     * @return totalProperties the total number of properties
     */
    private int getAllAvailableProperties()
    {
        int totalProperties = properties.size();
        return totalProperties;
    }

    /**
     * A method to get all properties that are entire homes and apartments.
     * @return the number of properties that are homes and apartments
     */
    private int getAllHomesApartments()
    {
        int totalHomeApartments = 0;
        for (AirbnbListing listing : properties)
        {
            if (listing.getRoom_type().equals("Entire home/apt"))
            {
                totalHomeApartments++;
            }
        }
        return totalHomeApartments;
    }

    /**
     * A method to get the most expensive borough from the price range
     * @return borough the most expensive borough
     */
    private String getMostExpensive()
    {
        int totalCost1 = 0;
        int totalCost2 = 0;
        String borough = "";
        for (AirbnbListing listing : properties)
        {
            totalCost2 = listing.getPrice() * listing.getMinimumNights();
            if (totalCost2 >= totalCost1)
            {
                borough = listing.getNeighbourhood();
                totalCost1 = totalCost2;
            }
        }
        return borough;
    }
    
    /**
     * A method to get the average price of all properties
     * @return totalCost / properties.size() the average cost of all properties
     */
    private int  getAveragePrice()
    {
        int totalCost = 0;
        for (AirbnbListing listing : properties)
        {
            totalCost += listing.getPrice();
        }
        return totalCost / properties.size();
    }
    
    /**
     * A method to get all private rooms within the price range
     * @return totalPrivateRooms the number of private rooms
     */
    private int getAllPrivateRooms()
    {
        int totalPrivateRooms = 0;
        for (AirbnbListing listing : properties)
        {
            if (listing.getRoom_type().equals("Private room"))
            {
                totalPrivateRooms++;
            }
        }
        return totalPrivateRooms;
    }
    
    /**
     * A method to get the least expensive borough from the price range
     * @return borough the least expensive borough
     */
    private String getLeastExpensive()
    {
        int totalCost1 = 99999;
        int totalCost2 = 0;
        String borough = "";
        for (AirbnbListing listing : properties)
        {
            totalCost2 = listing.getPrice() * listing.getMinimumNights();
            if (totalCost2 <= totalCost1)
            {
                borough = listing.getNeighbourhood();
                totalCost1 = totalCost2;
            }
        }
        return borough;
    }

    /**
     * Calculates the average minimum nights
     * @return The average minimum nights of all properties
     */
    private int averageMinNights()
    {
        int totalMinNight = 0;
        for (AirbnbListing listing : properties)
        {
            totalMinNight += listing.getMinimumNights();
        }
        return totalMinNight / properties.size(); 
    }
}
