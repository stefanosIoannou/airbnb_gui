package src;

/**
 * src.Refreshable interface that has a refresh method as abstract
 *
 * @author Stefanos Ioannou 
 * @version 25.03.2020
 */
public interface Refreshable {

    /**
     * This method is called when the values in from and to price change
     * and the range is valid
     */
    abstract void refresh();
}
