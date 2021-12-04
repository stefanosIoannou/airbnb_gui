package src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;

/**
 * The src.GUI class is the main class of the application and the one that must be
 * launched to display the gui. It contains the main window who's appearance
 * is enforced by the coursework requirements. The whole application
 * relies on this window calling the "getDisplay" methods of various panels and 
 * displaying it in the center of a borderpane. The user can sort which properties
 * are displayed in the other panels from drop down boxes at the top, and rotate 
 * through panels with buttons at the bottom.
 * 
 * @author Alexandre Chouman , Stefanos Ioannou , Charles Salser 
 */

public class GUI extends Application {
    private final WelcomePanel welcomePanel;

    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 700;

    private ComboBox<String> fromBox;
    private ComboBox<String> toBox;
    private Button leftPanelButton;
    private Button rightPanelButton;
    private Label selectedPriceRange;
    private Label noPriceLabel;

    private BorderPane centerPane;
    public static Scene scene;

    private final ArrayList<Panel> panels;
    private int currentPanelIndex = 0;

    private final HashMap<String, Integer> valueMap; //stores combobox values

    // A list of all the listings in the file
    private static List<AirbnbListing> allListings;

    /**
     * Main method
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Constructor of the src.GUI class. It sets up the building of the window.
     */
    public GUI(){

        panels = new ArrayList<>();
        valueMap = new HashMap<>();
        fillMap();

        welcomePanel = new WelcomePanel();
        MapPanel mapPanel = new MapPanel();
        StatisticsPanel statsPanel = new StatisticsPanel();
        UploadPanel uploadPanel = new UploadPanel();

        panels.add(welcomePanel);
        panels.add(mapPanel);
        panels.add(statsPanel);
        panels.add(uploadPanel);
    }

    /**
     * The start method, which builds the entire window.
     * @param stage The stage to be created
     */
    @Override
    public void start(Stage stage) throws IOException {
        // setting up first comboBox
        // Loads the file with the listings
        AirbnbDataLoader loader = new AirbnbDataLoader();
        allListings = loader.load();

        //setting up first comboBox
        Label fromLabel = new Label("From: ");
        fromBox = new ComboBox<>();
        fromBox.getItems().addAll("-","0£","10£","20£","30£","50£","75£","100£","200£","500£","1000£","1000£ +");
        fromBox.setValue("-");
        fromBox.setOnAction((e) -> {
                boxSelected();
            });

        // second comboBox    
        Label toLabel = new Label("To: ");
        toBox = new ComboBox<>();
        toBox.getItems().addAll("-","0£","10£","20£","30£","50£","75£","100£","200£","500£","1000£","1000£ +");
        toBox.setValue("1000£ +");
        toBox.setOnAction((e) -> {
                boxSelected();
            });

        leftPanelButton = new Button("<<");
        leftPanelButton.setDisable(true);
        leftPanelButton.setOnAction(this::previousPanel);

        rightPanelButton = new Button(">>");
        rightPanelButton.setOnAction(this::nextPanel);
        rightPanelButton.setDisable(true);

        noPriceLabel = new Label("(No valid price range selected)");
        noPriceLabel.getStyleClass().add("noPriceLabel");

        BorderPane topPane = new BorderPane();
        topPane.getStyleClass().add("topPane");
        HBox topHbox = new HBox();
        topHbox.getStyleClass().add("hbox");
        topHbox.getChildren().add(noPriceLabel);
        topHbox.getChildren().add(fromLabel);
        topHbox.getChildren().add(fromBox);
        topHbox.getChildren().add(toLabel);
        topHbox.getChildren().add(toBox);
        topPane.setRight(topHbox);

        selectedPriceRange = new Label(getPriceRangeString());
        selectedPriceRange.getStyleClass().add("selectedPriceRange");
        topPane.setLeft(selectedPriceRange);

        MenuBar menuBar = new MenuBar();
        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About the team");
        about.setOnAction(this::aboutAlert);

        MenuItem version = new MenuItem("Version info");
        version.setOnAction(this::versionAlert);
        helpMenu.getItems().addAll(about,version);

        menuBar.getMenus().add(helpMenu);
        topPane.setTop(menuBar);

        BorderPane bottomPane = new BorderPane();

        bottomPane.setLeft(leftPanelButton);
        bottomPane.setRight(rightPanelButton);
        bottomPane.getStyleClass().add("bottomPane");

        centerPane = new BorderPane();
        centerPane.setCenter(panels.get(currentPanelIndex).getDisplay());
        centerPane.getStyleClass().add("centerPaneStyle");

        BorderPane root = new BorderPane();

        //group all those panes above into root
        root.setTop(topPane);
        root.setBottom(bottomPane);
        root.setCenter(centerPane);
        root.getStyleClass().add("rootStyle");

        scene = new Scene(root, getWidth(), getHeight());
        scene.getStylesheets().add("style/gui.css");
        root.getStyleClass().add("rootStyle");
        stage.setScene(scene);
        stage.setTitle("AirBnb");
        stage.setResizable(true);

        welcomeSetUp();

        stage.show();
    }

    /**
     * Return the all listings in the CSV file
     * @return Listings in the CSV file
     */
    public static List<AirbnbListing> getListingsFromFile(){
        return allListings;
    }

    /**
     * This is needed because unlike most cases, we sometimes need user action
     * on the welcome panel to affect the gui such as making the comboboxes open... ,
     * and the welcomePanel has no src.GUI field. This method just allows
     * for the combobox to drop down when something inside the welcomePanel 
     * is clicked.
     */
    private void welcomeSetUp(){
        welcomePanel.getInstructionLabel().setOnMouseClicked(this:: instructionInteraction);

    }

    /**
     * The action to be performed when the instructionLabel is clicked.
     */
    private void instructionInteraction(MouseEvent e){
        fromBox.show();
    }

    /**
     * @return DEFAULT_WIDTH the default width of the window
     */
    private int getWidth(){
        return DEFAULT_WIDTH;

    }

    /**
     * @return DEFAULT_HEIGHT the default height of the window
     */
    private int getHeight(){
        return DEFAULT_HEIGHT;
    }

    /**
     * Do all necessary operations before returning the next panel, which
     * will then be on screen in another call.
     * @return src.Panel the next panel.
     */
    private Panel getNextPanel(){
        changeCurrentPanelActivity();
        incrementPanelIndex();
        Panel nextPanel = panels.get(currentPanelIndex);
        nextPanel.setActive(true);

        return nextPanel;
    }

    /**
     * Change the current panel to inactive.
     */
    private void changeCurrentPanelActivity(){
        Panel currentPanel = panels.get(currentPanelIndex);
        currentPanel.setActive(false);
        currentPanel.checkActivity();
    }

    /**
     * Do all necessary operations before returning the previous panel, which
     * will then be on screen in another call.
     * @return src.Panel the previous panel.
     */
    private Panel getPreviousPanel(){
        changeCurrentPanelActivity();
        decrementPanelIndex();
        Panel previousPanel = panels.get(currentPanelIndex);
        previousPanel.setActive(true);

        return previousPanel;
    }

    /**
     * Change the current panel to the next panel.
     */
    private void nextPanel(ActionEvent event){
        try {
            centerPane.setCenter(getNextPanel().getDisplay());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change the current panel to the previous panel.
     */
    private void previousPanel(ActionEvent event){
        try {
            Panel previousPanel = getPreviousPanel();
            previousPanel.checkActivity();
            centerPane.setCenter(previousPanel.getDisplay());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }     

    /**
     * Increments the currentPanelIndex, in a way that it loops back to the
     * first one if it reaches the end of the list.
     */
    private void incrementPanelIndex(){
        currentPanelIndex = (currentPanelIndex + 1) % (panels.size());
    }

    /**
     * Decrements the currentPanelIndex, in a way that it loops back to the
     * first one if it reaches the start of the list.
     */
    private void decrementPanelIndex(){
        if(currentPanelIndex == 0){
            currentPanelIndex = panels.size()-1;
        }else{
            currentPanelIndex -- ;
        }
    }

    /**
     * called whenever one of the two comboBoxes is selected. Calls one of 
     * two possible methods depending on the validity of the selection.
     */
    private void boxSelected(){
        if(selectionIsValid()){
            validBoxSelection();
        }else{
            invalidBoxSelection();
        }      
    }

    /**
     * The transition to play when a correct selection of values in the boxes
     * is made.
     */
    private void fadeInstruction(){
        FadeTransition ft = new FadeTransition(Duration.millis(700), welcomePanel.getInstructionLabel());
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
    }

    /**
     * Called when a correct selection of values is made. Enables cycling
     * through panels, and disables any action during 700 millis, so that the 
     * transition may play before the label is removed from the screen. 
     */
    private void validBoxSelection(){
        rightPanelButton.setDisable(false);
        leftPanelButton.setDisable(false);
        selectedPriceRange.setText(getPriceRangeString());
        noPriceLabel.setVisible(false);

        fadeInstruction();
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                @Override
                public void run() {
                    welcomePanel.getInstructionLabel().setVisible(false);
                    // the transition isn't seen if we call setvisible
                    // right away so we delay it; code from:
                    // https://stackoverflow.com/questions/2258066/
                    // java-run-a-function-after-a-specific-number-of-seconds
                }
            }, 
            700 
        );
    }

    /**
     * Called when an invalid couple of values is selected by the user.
     * an alerts appears informing the user of this.
     */
    private void invalidBoxSelection(){
        Alert alert = new Alert(AlertType.WARNING, "Please select correct values");
        alert.setHeaderText("Invalid Selection");
        alert.show();
        rightPanelButton.setDisable(true);
        leftPanelButton.setDisable(true);
        noPriceLabel.setVisible(true);
    }

    /**
     * Checks that the current selection of values is valid, that is that
     * the from value is inferior or equal to the to value.
     * @return isValid, true if the selection is valid.
     */
    private boolean selectionIsValid() {
        boolean isValid = false;
        Panel.setCurrentFromValue(valueMap.get(fromBox.getValue()));
        Panel.setCurrentToValue(valueMap.get(toBox.getValue()));
        if (Panel.getCurrentFromValue() <= Panel.getCurrentToValue()){
            isValid = true;
            refreshCurrentPanel();
        }
        return isValid;
    }

    /**
     * Refresh the current panel
     * src.Panel will only refresh if the src.Panel implements src.Refreshable.
     */
    private void refreshCurrentPanel(){
        Panel currentPanel = panels.get(currentPanelIndex);
        if(currentPanel instanceof Refreshable){
            ((Refreshable) currentPanel).refresh();
        }
    }

    /**
     * Puts the value pairs inside the valueMap.
     */
    private void fillMap(){
        valueMap.put("-",0);
        valueMap.put("0£",0);
        valueMap.put("10£",10);
        valueMap.put("20£",20);
        valueMap.put("30£",30);
        valueMap.put("50£",50);
        valueMap.put("75£",75);
        valueMap.put("100£",100);
        valueMap.put("200£",200);
        valueMap.put("500£",500);
        valueMap.put("1000£",1000);
        valueMap.put("1000£ +",99999999);
    }

    /**
     * @return the string detailing the current selected price range.
     */
    public String getPriceRangeString(){
        if(Panel.getCurrentToValue() == 99999999){
            return "Selected: " + Panel.getCurrentFromValue() + "£ - ";
        }
        return "Selected: " + Panel.getCurrentFromValue() + "£ - " + Panel.getCurrentToValue() + "£";
    }

    /**
     * Called when a user clicks "about" in the help menu. Gives basic info
     * about authors.
     */
    private void aboutAlert(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION, """
                NullPointers
                 Stefanos Ioannou 
                 Alexandre Chouman 
                 Charles Salser 
                 Aaron Ram """);
        alert.setHeaderText("Project by:");
        alert.show();
    }

    /**
     * Called when a user clicks the version info in the help menu.
     */
    private void versionAlert(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION, "Version 1.0");
        alert.setHeaderText("Current version:");
        alert.show();
    }
}