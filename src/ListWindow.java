package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * This is the class that creates the window that appears when a borough is clicked on.
 * The window is loaded from the src.ListWindow.fxl file.
 * Controller of the fxml file is the src.ListWindowController.class
 *
 * @author Stefanos Ioannou 
 * @version 21.03.2020
 */
public class ListWindow extends Application implements ChildWindow {

    // The controller for the src.ListWindow
    private ListWindowController windowController;

    /**
     * Start building the window
     * @param primaryStage The stage the scene is to open in
     * @throws Exception FXML Loader
    */
    @Override
    public void start(Object primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListWindow.fxml"));
        loader.setController(windowController);
        primaryStage = loader.load();
        primaryStage.show();
        addToParent(primaryStage);
    }

    /**
     * Constructor fo the src.ListWindow class
     * @param borough The borough the properties in the window are from
     */
    public ListWindow(String borough){
        windowController = new ListWindowController(borough);
    }

}
