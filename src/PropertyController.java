package src;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.text.DecimalFormat;

/**
 * The Property Controller of the listing.fxml file
 * The controller inserts basic information into the listing.
 * The controller also creates a new window with all the details of the property
 * if the listing is selected
 *
 * @author Stefanos Ioannou , Charles Salser 
 * @version 25.03.2020
 */
public class PropertyController implements ChildWindow {
    // The format of the price value
    private static final DecimalFormat DF = new DecimalFormat("#.00");
    // The property the listing is about
    private final AirbnbListing PROPERTY;

    @FXML
    private Label hostGUI;
    @FXML
    private Label priceGUI;
    @FXML
    private Label nightsGUI;
    @FXML
    private Label reviewsGUI;

    // New window showing the properties details
    @FXML
    private Button showDescription;

    /**
     * Constructor of the src.PropertyController Class
     * @param airbnbListing A listing in the CSV file
     */
    public PropertyController(AirbnbListing airbnbListing) {
        this.PROPERTY = airbnbListing;
    }

    /**
     * Initialize the listing
     * Set up data from the CSV to the listing src.GUI
     * Set up an action listener to create a new window to show details of the property
     */
    @FXML
    void initialize(){
        priceGUI.setText("£ " + DF.format(PROPERTY.getPrice()));
        hostGUI.setText(PROPERTY.getHost_name());
        nightsGUI.setText(String.valueOf(PROPERTY.getMinimumNights()));
        reviewsGUI.setText(String.valueOf(PROPERTY.getNumberOfReviews()));

        showDescription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label secondLabel = new Label(PROPERTY.display());

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getStylesheets().add("style/propertyController.css");
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, 450, 350);

                Object newWindow = new Stage();
                newWindow.setTitle(PROPERTY.getName());
                newWindow.setScene(secondScene);

                newWindow.show();
                addToParent(newWindow);
            }
        });
    }
}
