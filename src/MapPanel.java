package src;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * The src.MapPanel class loads the pane containing the map
 * and instantiates the src.MapController, the controller of the fxml file
 *
 * @author Stefanos Ioannou 
 * @version 25.03.2020
 */
public class MapPanel extends Panel implements Refreshable{

    // Controller of the map
    private MapController mapController;

    /**
     * Load the pane with the map and assigns the controller
      */
    @Override
    public Pane getDisplay() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LondonMap.fxml"));
        mapController = new MapController();
        loader.setController(mapController); //define the controller
        return loader.load();
    }

    /**
     * Re-initialise the map
     */
    @Override
    public void refresh(){
        mapController.refreshMap();
    }

    /**
     * Check the activity of the window
     * If the panel is not active close all the child windows
     */
    @Override
    public void checkActivity() {
        if(!isActive()){
           closeAllChildWindows();
        }
    }
}