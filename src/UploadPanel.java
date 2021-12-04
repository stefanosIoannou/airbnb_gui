package src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.io.FileWriter;
import com.opencsv.CSVWriter;
import java.io.File;
import java.net.URL;
import java.net.URISyntaxException;

/**
 * The src.UploadPanel class implements a panel that allows a user to "upload"
 * a property to the airbnb database; since the database here takes the form of
 * a csv file, this panel essentially collects data from the user and adds it to 
 * csv file.
 * @author Alexandre Chouman , Aaron Ram 
 */

public class UploadPanel extends Panel 
{
    private Label signInLabel;

    private GridPane gridPane;
    private BorderPane mainPane;
    private GridPane loginGrid;
    private TextField usernameText;
    private PasswordField passwordText;
    private Label charLimit;
    private TextField propertyName, hostFullName, adress, minimumNights, price, availability365;
    private Label propertyNameLabel, hostFullNameLabel, adressLabel, neighborhoodLabel, roomTypeLabel, minimumNightsLabel, priceLabel, availability365Label;
    private ComboBox neighborhood, roomType;
    private Button addProperty;

    private String username = null;
    private String password = null;
    private boolean loggedIn = false;

    @Override
    public Pane getDisplay() {

        loginGrid = new GridPane();
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);

        Label usernameLabel = new Label("Username: ");
        usernameText = new TextField();
        Label passwordLabel = new Label("Password: ");
        passwordText = new PasswordField();
        charLimit = new Label("(min 8 characters)");
        loginGrid.add(usernameLabel,0,0);
        loginGrid.add(usernameText,1,0);
        loginGrid.add(passwordLabel,0,1);
        loginGrid.add(passwordText,1,1);
        loginGrid.add(charLimit,2,1);

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(this::confirmLogin);
        confirmButton.getStyleClass().add("confirm");

        Button refuseButton = new Button("Login as guest");
        refuseButton.setOnAction(this::refuseLogin);
        refuseButton.getStyleClass().add("guest");

        loginGrid.add(confirmButton,0,2);
        loginGrid.add(refuseButton,1,2);
        signInLabel = new Label();
        signInLabel.getStyleClass().add("signInLabel");

        mainPane = new BorderPane();
        mainPane.getStylesheets().add("style/uploadPanel.css");
        mainPane.getStyleClass().add("mainPane");
        mainPane.setLeft(loginGrid);

        gridPane = new GridPane();

        gridPane.setVisible(false);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        propertyName = new TextField();
        propertyNameLabel = new Label("Name your listing: ");
        gridPane.setHalignment(propertyNameLabel, HPos.RIGHT);
        gridPane.add(propertyName,1,0);
        gridPane.add(propertyNameLabel,0,0);

        hostFullName = new TextField();
        hostFullNameLabel = new Label("Your full Name: ");
        gridPane.setHalignment(hostFullNameLabel, HPos.RIGHT);
        gridPane.add(hostFullName,1,1);
        gridPane.add(hostFullNameLabel,0,1);

        adress = new TextField();
        adressLabel = new Label("Adress of the property: ");
        gridPane.setHalignment(adressLabel, HPos.RIGHT);
        gridPane.add(adress,1,2);
        gridPane.add(adressLabel,0,2);

        neighborhood = new ComboBox();
        neighborhood.getItems().addAll("-","City of London","Barking and Dagenham","Barnet","Bexley","Brent","Bromley","Camden","Croydon","Ealing","Enfield","Greenwich","Hackney","Hammersmith and Fulham","Haringey","Harrow","Havering","Hillingdon","Hounslow","Islington","Kensington and Chelsea","Kingston upon Thames","Lambeth","Lewisham","Merton","Newham","Redbridge","Richmond upon Thames","Southwark","Sutton","Tower Hamlets","Waltham Forest","Wandsworth","Westminster");
        neighborhood.setValue("-");

        neighborhoodLabel = new Label("Choose the neigbourhood of your property: ");
        gridPane.setHalignment(neighborhoodLabel, HPos.RIGHT);
        gridPane.add(neighborhood,1,3);
        gridPane.add(neighborhoodLabel,0,3);

        roomType = new ComboBox();
        roomType.getItems().addAll("-","Entire place","Private room","Shared room");
        roomType.setValue("-");
        roomTypeLabel = new Label("Choose the room type: ");
        gridPane.setHalignment(roomTypeLabel, HPos.RIGHT);
        gridPane.add(roomType,1,4);
        gridPane.add(roomTypeLabel,0,4);

        price = new TextField();
        priceLabel = new Label("Price per night: ");
        gridPane.setHalignment(priceLabel, HPos.RIGHT);
        gridPane.add(price,1,5);
        gridPane.add(priceLabel,0,5);

        minimumNights = new TextField();
        minimumNightsLabel = new Label("Choose the number of minimum nights: ");
        gridPane.setHalignment(minimumNightsLabel, HPos.RIGHT);
        gridPane.add(minimumNights,1,6);
        gridPane.add(minimumNightsLabel,0,6);

        availability365 = new TextField();
        availability365Label = new Label("How many days a year is the property available? ");
        gridPane.setHalignment(availability365Label, HPos.RIGHT);
        gridPane.add(availability365,1,7);
        gridPane.add(availability365Label,0,7);

        addProperty = new Button("Upload my property!");
        gridPane.add(addProperty,1,8);
        addProperty.setOnAction(this::addPropertyToFile);
        addProperty.getStyleClass().add("upload");

        mainPane.setCenter(gridPane);
        mainPane.getStyleClass().add("mainPane");
        
        //testing purposes below
        
        if(!loggedIn){
            return mainPane;
        }else{
            loginGrid.setVisible(false);
            signInLabel.setText("Logged in as: " + username);
            mainPane.setLeft(signInLabel);
            gridPane.setVisible(true);
            return mainPane;
        }
    }

    /**
     * Check the activity of the panel
     */
    @Override
    public void checkActivity() {
        isActive();
    }

    /**
     * Checks whether to update src.GUI if the password check value comes back as true
     * @param event the user presses the login button
     */
    private void confirmLogin(ActionEvent event){
        String user = usernameText.getText().trim();
        boolean update = loginCheck(passwordText.getText().trim());
        if(update == true && !user.equals(""))
        {
            updateGraphic(user);
        }
        usernameText.clear();
        passwordText.clear();
    }

    /**
     * Logs in the user as a guest
     * @param event the user selects the continue as guest option
     */
    private void refuseLogin(ActionEvent event){
        signInLabel.setText("Logged in as: guest");
        username = "guest";
        loggedIn = true;
        loginGrid.setVisible(false);
        mainPane.setLeft(signInLabel);
        gridPane.setVisible(true);
    }

    /**
     * Checks the password is more than eight characters.
     * @param password the password of the user
     * @return loggedIn the value of whether the user has successfully logged in or not
     */
    private boolean loginCheck(String password){
        String pass = password;
        if(pass.length()  >= 8){
            password = pass;
            loggedIn = true;
        }
        return loggedIn;
    }
    
    /**
     * Updates the src.GUI if the user has logged in
     * @param username the username of the user
     */
    private void updateGraphic(String username)
    {
        String user = username;
        signInLabel.setText("logged in as: " + user);
        username = user;
        loginGrid.setVisible(false);
        mainPane.setLeft(signInLabel);
        gridPane.setVisible(true);
    }

    /**
     * Calls the method that adds a property to the file, and notifies
     * the user of the outcome of the attempt to add a property to the
     * database.
     */
    private void addPropertyToFile(ActionEvent event)
    {
        boolean success = inputInfo(propertyName.getText(), hostFullName.getText(), neighborhood.getValue().toString(), roomType.getValue().toString(), price.getText(), minimumNights.getText(), availability365.getText());
        if (success == true)
        {
            successfulAdd();
        }
        else
        {
            failedAdd();
        }
    }
    
    /**
     * Tries to add a property to the csv file.
     * @return true if the adding process was successful.
     */
    private boolean inputInfo(String propertyName, String hostFullName, String neighborhood, String roomType, String price, String minimumNights, String availability365)
    {
        boolean success = false;
        if (fieldsCorrect(propertyName, hostFullName, neighborhood, roomType, price, minimumNights, availability365) == true)
        {
            URL url = getClass().getResource("res/airbnb-london.csv");
            try(CSVWriter writer = new CSVWriter(new FileWriter(new File(url.toURI()).getAbsolutePath(),true)))
            {
                String[] property= new String[] {"", propertyName, "", hostFullName, neighborhood, "0", "0", roomType, price, minimumNights, "", "", "", "", availability365};
                writer.writeNext(property,false);
                success = true;
            }
            catch (IOException | URISyntaxException e)
            {
                return success;
            }
        }
        return success;
    }

    /**
     * Checks that all field values are within legal parameters
     * @param propertyName The name of the property
     * @param hostFullName The name of the host
     * @param neighborhood The name of the neighbourhood
     * @param roomType The type of room
     * @param price The price of the property
     * @param minimumNights The minimum nights one can stay
     * @param availability365 The availability of the property
     * @return true if all fields are valid, false otherwise
     */
    private boolean fieldsCorrect(String propertyName, String hostFullName, String neighborhood, String roomType, String price, String minimumNights, String availability365){
        if(!(isInteger(price) && isInteger(minimumNights) && isInteger(availability365))){
            return false;
        }else if(neighborhood.equals("-") || roomType.equals("-")){
            return false;
        }else if(propertyName.trim().isEmpty() || hostFullName.trim().isEmpty()){
            return false;
        }else if((Integer.parseInt(price) <= 0) || (Integer.parseInt(minimumNights) <0) || (Integer.parseInt(availability365) <0)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Alert shown if invalid values were entered.
     */
    private void failedAdd(){
        Alert alert = new Alert(AlertType.WARNING, "Couldn't upload your property, try again...");
        alert.setHeaderText("Something went wrong...");
        alert.show();
        clearAll();
    }

    /**
     * Alert shown if correct values were entered.
     */
    private void successfulAdd(){
        Alert alert = new Alert(AlertType.INFORMATION, "Your property was successfully uploaded!");
        alert.setHeaderText("Success!");
        alert.show();
        clearAll();
    }

    /**
     * Clear all fields.
     */
    private void clearAll(){
        propertyName.clear();
        hostFullName.clear();
        adress.clear();
        price.clear();
        minimumNights.clear();
        availability365.clear();
        neighborhood.setValue("-");
        roomType.setValue("-");

    }

    /**
     * Checks that a provided string is an integer value.
     * @return true if the text is an integer.
     */
    private boolean isInteger(String text){
        if(text.trim().isEmpty()){
            return false;
        } else if(text.matches("\\d*")){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * This method exists for testing purposes
     * Logs in test password
     * @param password The password to be entered
     */
    public void setLogin(String password)
    {
        loginCheck(password);
    }
    
    /**
     * This method exists for testing purposes
     * Checks that the user has logged in or not
     * @return loggedIn The boolean value for whether the user has logged in or not
     */
    public boolean getLoggedIn()
    {
        return loggedIn;
    }
    
    /**
     * This method exists for testing purposes
     * Tests inputting the test property
     * @param propertyName The name of the property
     * @param hostFullName The name of the host
     * @param neighborhood The name of the neighbourhood
     * @param roomType The type of room
     * @param price The price of the property
     * @param minimumNights The minimum nights one can stay
     * @param availability365 The availability of the property
     */
    public void testInput(String propertyName, String hostFullName, String neighborhood, String roomType, String price, String minimumNights, String availability365)
    {
        inputInfo(propertyName, hostFullName, neighborhood, roomType, price, minimumNights, availability365);
    }
}
