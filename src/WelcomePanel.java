package src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.Cursor;

/**
 * The Welcome panel contains a little bit of information for the user regarding
 * what they must do to user our application. It is the landing screen when launching
 * the javafx application but takes place inside a common window like all the other 
 * panels.
 * @author Alexandre Chouman k1902204
 */
public class WelcomePanel extends Panel
{
    private BorderPane welcomePanel;
    private VBox labels;

    private Label welcomeMessageLabel;
    private Label instructionLabel;
    private Label movePanelsLabel;

    @Override
    public Pane getDisplay(){
        welcomePanel = new BorderPane();
        welcomePanel.getStylesheets().add("style/welcomePanel.css");
        welcomePanel.getStyleClass().add("panel");

        labels = new VBox();

        welcomeMessageLabel = new Label("Welcome to AirBnb!");
        welcomeMessageLabel.getStyleClass().add("welcomeMessageLabel");

        instructionLabel = new Label("Select a price range");
        instructionLabel.getStyleClass().add("instructionLabel");
        instructionLabel.setOnMouseEntered(this::instructionHover);
        instructionLabel.setOnMouseExited(this::instructionNotHover);

        movePanelsLabel = new Label("Move through the panels to find or put up an AirBnb.");
        movePanelsLabel.getStyleClass().add("movePanelsLabel");

        labels.getChildren().addAll(welcomeMessageLabel,instructionLabel);
        welcomePanel.setCenter(labels);
        labels.setAlignment(Pos.CENTER);

        welcomePanel.setBottom(movePanelsLabel);
        welcomePanel.setAlignment(movePanelsLabel,Pos.CENTER);

        return welcomePanel;
    }

    /**
     * Checks if the panel is active
     */
    @Override
    public void checkActivity() {
        isActive();
    }

    /**
     * Instructions when a mouse hovers the "select a price range" text
     */
    private void instructionHover(MouseEvent e){
        instructionLabel.getStyleClass().remove("instructionLabelNotHover");
        instructionLabel.getStyleClass().add("instructionLabelHover");
        welcomePanel.setCursor(Cursor.HAND);
    }

    /**
     * Instructions when a mouse leaves the "select price range" text
     */
    private void instructionNotHover(MouseEvent e){
        instructionLabel.getStyleClass().remove("instructionLabelHover");
        instructionLabel.getStyleClass().add("instructionLabelNotHover");
        welcomePanel.setCursor(Cursor.DEFAULT);
    }

    /**
     * @return Label the instruction label.
     */
    public Label getInstructionLabel(){
        return instructionLabel;
    }

}
