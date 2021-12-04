package src;

import javafx.stage.Stage;

/**
 * The src.ChildWindow interface implements a method to add child windows
 * to the main window
 *
 * @author Stefanos Ioannou 
 * @version 25.03.2020
 */
public interface ChildWindow {

    /**
     * Add a child window to the main window with the src.Panel
     * @param newChild The child window to add
     */
    default void addToParent(Object newChild){
        Panel.addChildWindow(newChild);
    }
}
