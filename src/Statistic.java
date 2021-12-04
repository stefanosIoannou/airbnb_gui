package src;

/**
* The src.Statistic Class, a class to represent each statistic
* displayed on the statsPanel. Purpose was to make it easier 
* to store stats in the arrayList of hidden stats.
* @author Charles Salser 
* @version 27.03.2020
*/
public class Statistic
{
    private boolean isShown;
    private String name;
    private String valueOf;

    /**
     * Constructor of the src.Statistic Class
     * @param nameIn The name of the statistic, and valueIn the value of the statistic
     */
    public Statistic(String nameIn, String valueIn)
    {
        name = nameIn;
        valueOf = valueIn;
    }
    
    public boolean isShown()
    {
        return isShown;
    }
    
    public void setShown()
    {
        isShown = !isShown;
    }
    
    public void setValue(String valueIn)
    {
        valueOf = valueIn;
    }
    
    /**
     * Overrides the toString method for ease of display them.
     */
    @Override
    public String toString()
    {
        return "" + name +":\n\n" + valueOf;
    }
}
