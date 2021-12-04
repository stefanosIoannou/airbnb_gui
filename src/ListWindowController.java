package src;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * src.ListWindowController class handles the src.ListWindow.
 * The controller calculates pagination and calls a method to load the listings
 *
 * @author Stefanos Ioannou 
 * @version 25.03.2020
 */
public class ListWindowController implements PropertyManipulation {

    // Properties filtered by borough clicked
    private List<AirbnbListing> properties;

    // The title of the window
    private final String BOROUGH;

    // The limit of listings per page
    private static final int LISTING_INDEX_LIMIT = 10;

    @FXML
    private ScrollPane scrollablePane;

    @FXML
    private VBox content;

    @FXML
    private Stage window;

    @FXML
    private Pagination pageSlider;

    @FXML
    private BorderPane mainPane;

    // Sorting

    @FXML
    private ChoiceBox sortType;

    @FXML
    private ChoiceBox sortBy;

    // Maps for choice box options
    private HashMap<String, SortType> typeOptions;
    private HashMap<String, SortBy> columnOptions;


    /**
     * Constructor of the src.ListWindowController class.
     *
     * @param borough The borough the properties in the window are from
     */
    public ListWindowController(String borough) {
        BOROUGH = borough;
        properties = new ArrayList<>();
    }

    /**
     * Initialize the window
     * Check if there are any properties to show
     * Set the title, calculate pagination and add sorting options
     */
    @FXML
    void initialize() {
        setWindowTitle(BOROUGH);
        getData(BOROUGH);

        // Check if there are any properties to show
        if (!properties.isEmpty()) {
            addSortingOptions();
            addChoiceBoxListeners();
            setUpPagination();
        }
        else {
            setUpErrorPane();
        }
    }

    /**
     * Set the title of the window to the neighbourhood's full name
     *
     * @param borough Neighbourhoods full name
     */
    @FXML
    private void setWindowTitle(String borough) {
        window.setTitle(borough);
    }

    /**
     * Collect listings that have the neighbourhood selected
     *
     * @param text Neighbourhood full name
     */
    private void getData(String text) {
        for (AirbnbListing entry : getPropertiesFromRange(Panel.getCurrentFromValue(), Panel.getCurrentToValue())) {
            if (text.equals(entry.getNeighbourhood())) {
                properties.add(entry);
            }
        }
    }

    /**
     * Set up the pane to be displayed if the borough chosen has no properties to show
     */
    private void setUpErrorPane() {
        ImageView imageView = new ImageView("res/noProperties.png");
        mainPane.setTop(null);
        mainPane.setBottom(null);
        mainPane.setCenter(imageView);
        mainPane.setPrefHeight(imageView.getFitHeight());
    }

    /**
     * Set up pagination for the Listings Window
     * Pages are calculated based on the limit of boroughs per page
     * There should be at least on page
     */
    private void setUpPagination() {
        int pages = (int) Math.ceil(properties.size() / LISTING_INDEX_LIMIT);
        if(pages == 0){
            pages = 1;
        }
        pageSlider.setPageCount(pages);
        pageSlider.setPageFactory(this::createPage);
    }

    /**
     * Create a page in the List Window
     * @param page The index of the page to be created
     * @return The newly created page
     */
    private VBox createPage(int page) {
        page +=1;
        VBox newPage = new VBox();
        for(int i = (page-1)*LISTING_INDEX_LIMIT; i < page*LISTING_INDEX_LIMIT;i++){
            loadListing(newPage,i);
        }
        goToTop();
        content.getChildren().clear();
        content.getChildren().addAll(newPage.getChildren());
        return newPage;
    }

    /**
     * Go to the top of the panel
     */
    private void goToTop(){
        scrollablePane.setVvalue(scrollablePane.getVmin());
    }

    /**
     * Set action events to the drop-downs
     */
    private void addChoiceBoxListeners() {
        sortType.setOnAction(actionEvent -> sortListings());
        sortBy.setOnAction(actionEvent -> sortListings());
    }

    /**
     * Sort the list of properties based on the sorting options chosen
     * After sorting the pane goes to page index 0
     */
    private void sortListings(){
        sortProperties();
        setUpPagination();
    }

    /**
     * Load a listings with property information
     * @param currentPage The page the listing would be displayed
     * @param i The index of the property to display
     */
    public void loadListing(VBox currentPage,int i) {
            try {
                currentPage.getChildren().add(properties.get(i).createListing());
            } catch (Exception e) {
                return;
            }
    }

    /**
     * Add the sorting options to the choice boxes in the list window
     */
    private void addSortingOptions() {
        columnOptions = new HashMap();
        for (SortBy column : SortBy.values()) {
            columnOptions.put(column.toString(), column);
        }
        sortBy.getItems().addAll(columnOptions.keySet());

        typeOptions = new HashMap<String, SortType>();
        for (SortType type : SortType.values()) {
            typeOptions.put(type.toString(), type);
        }
        sortType.getItems().addAll(typeOptions.keySet());
    }

    /**
     * Sort properties based on the sorting type and the column type chosen
     * Both type and column must be selected for the method to sort the properties
     */
    private void sortProperties() {
        SortType typeChoice = typeOptions.get(sortType.getValue());
        SortBy columnChoice = columnOptions.get(sortBy.getValue());

        //Check that are no null values
        if(!(typeChoice == null || columnChoice == null)){
            List<AirbnbListing> sortedList = typeChoice.typeSort(columnChoice.byColumn(properties));
            properties.clear();
            properties.addAll(sortedList);
        }
    }

}
