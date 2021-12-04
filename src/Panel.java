package src;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The panel super class. The class holds variables for each panel
 * including the from and to price selected in the drop downs, the activity
 * of each panel, and the child windows of each panel.
 *
 * Children of the class can check activity and modify the childWindows
 *
 * @author Stefanos Ioannou  , Alexandre Chouman 
 * @version 25.03.2020
 */
public abstract class  Panel
{
    //Current from value selected by the user
    private static int currentFromValue = 0;

    // Current to value selected by the user
    private static int currentToValue = 99999999;

    // The default activity of the panel is false
    private boolean active = false;

    // Child windows of each src.Panel
    private static final ArrayList childStages = new ArrayList();


    /**
     * Method used to retrieve all the info to show about the current panel.
     * it differs for each panel.
     * @return Pane to shown
     * @throws IOException
     */
    public abstract Pane getDisplay() throws IOException;

    /**
     * Return the upper boundary of the price selected
     * @return Upper boundary of price selected by the user
     */
    public static int getCurrentFromValue(){
        return currentFromValue;
    }

    /**
     * Return the lower boundary of price selected
     * @return Lower boundary selected by the user
     */
    public static int getCurrentToValue(){
        return currentToValue;
    }

    /**
     * Set the lower bound if the price
     * @param price Lower bound price limit
     */
    public static void setCurrentToValue(int price){
        currentToValue = price;
    }

    /**
     * Set the upper bound of the price
     * @param price Upper bound of price limit
     */
    public static void setCurrentFromValue(int price){
        currentFromValue = price;
    }

    /**
     * Set the panels activity
     * @param currentlyActive Set the activity of the panel
     */
    protected void setActive(boolean currentlyActive){
        active=currentlyActive;
    }

    /**
     * Check the activity of the panel
     * This method is called when the panels are changed
     */
    protected abstract void checkActivity();

    /**
     * Check if the panel is active
     * @return True if the panel is active, False otherwise
     */
    protected boolean isActive(){
        return active;
    }

    /**
     * CLose all the child windows
     */
    protected void closeAllChildWindows(){
        for(Object window : childStages){
            window.close();
        }
        childStages.clear();
    }

    /**
     * Add a child window to the main window
     * @param newChild The window to add as a child
     */
    protected static void addChildWindow(Object newChild){
        childStages.add(newChild);
    }
}

