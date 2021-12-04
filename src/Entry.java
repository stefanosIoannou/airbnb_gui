package src;

import java.util.Comparator;

/**
 * The src.Entry class implements Comparable. Objects of this class are used in lists
 * for sorting based on the key.
 * The key can be double or String
 * By default the sorting method sorts keys that are double.
 * The class features a custom comparator that can compare Strings.
 * The src.Entry class sorts in ascending order by default.
 *
 * @author Stefanos Ioannou 
 * @version 25.03.2020
 */
public class Entry implements Comparable<Entry> {

    // The value of the src.Entry
    private final AirbnbListing VALUE;
    // The Key of the src.Entry
    private final double KEY;
    private final String STR_KEY;
    // Indicator of the type of Key
    private final boolean STRING_KEY;

    /**
     * Return if the key is String
     * @return true if the key is String, False otherwise
     */
    public boolean isString(){
        return STRING_KEY;
    }

    /**
     * src.Entry object constructor for double keys
     * @param key The key of the entry (double)
     * @param value The value of the entry
     */
    public Entry(double key, AirbnbListing value){
        this.STR_KEY = null;
        this.KEY = key;
        this.VALUE = value;
        STRING_KEY = false;
    }

    /**
     * src.Entry object constructor for String keys
     * @param name The key of the entry (String)
     * @param value The value of the entry
     */
    public Entry(String name,AirbnbListing value){
        this.KEY = 0;
        this.STR_KEY = name;
        this.VALUE = value;
        STRING_KEY = true;
    }

    // The comparator used to compare String keys.
    public static Comparator<Entry> sortString = new Comparator<Entry>() {
        @Override
        public int compare(Entry entry, Entry t1) {
            return entry.STR_KEY.compareToIgnoreCase(t1.STR_KEY);
        }

    };

    /**
     * Return the property (value) in the entry
     * @return The property in the entry
     */
    public AirbnbListing getValue() {
        return VALUE;
    }

    /**
     * Default Comparison
     * Compare and sort in ascending order of keys when the key is double
     * @param entry The entry to compare to
     * @return The index of the entry in a list
     */
    @Override
    public int compareTo(Entry entry) {
        if(this.KEY > entry.KEY){
            return 1;
        }
        else if(this.KEY < entry.KEY){
            return -1;
        }
        return 0;
    }

}
