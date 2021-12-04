package src;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * Interface used to colour the map according to how popular the neighbourhoods are
 *
 * @author Stefanos Ioannou 
 * @version 14.03.2020
 */
public interface MapColouring {
    // Starting color. This should not be set to black
    Color STARTING_COLOR = Color.rgb(0,120,150);
    // Colour for boroughs without properties
    String UNKNOWN_COLOR= "rgba(255, 255, 255, 0)";

    /**
     * Colour the specific borough in the map
     * @param borough Specific borough to colour
     */
    default void colorBorough(SVGPath borough,double fraction) {
        if (fraction == 0) {
            borough.setStyle("-fx-fill:" + UNKNOWN_COLOR + ";");
        } else {
            borough.setStyle("-fx-fill:" + "rgb(" + (STARTING_COLOR.getRed() / fraction) + "," + (STARTING_COLOR.getGreen() / fraction) + "," + (STARTING_COLOR.getBlue() / fraction) + ")" + ";");
        }
    }
}
