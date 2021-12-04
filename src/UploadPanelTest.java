package src;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The test class src.UploadPanelTest assesses the function of the Upload src.Panel.
 * It contains 11 tests that ensure that only legal values can be entered into any of the fields.
 *
 * @author  Aaron Ram 
 * @version 28.03.2020
 */
public class UploadPanelTest
{
    /**
     * Default constructor for test class src.UploadPanelTest
     */
    public UploadPanelTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    /**
     * Tests that login works with legal values
     */
    @Test
    public void testLogin()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.setLogin("12345678");
        assertEquals("The login works", true, testPanel.getLoggedIn());
    }
    
    /**
     * Tests that login doesn't work for password less thatn 8 characters
     */
    @Test
    public void testLoginFail()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.setLogin("1234");
        assertEquals("The login fails successfully", false, testPanel.getLoggedIn());
    }
    
    /**
     * Tests that item can be added with legal values
     */
    @Test
    public void testAddition()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "Brent", "Entire Place", "10", "10", "10");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "Brent", "Entire Place", "10", "10", "10"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        assertArrayEquals(testArray, actualArray);
    }
    
    /**
     * Tests that item can't be added with a negative price
     */
    @Test
    public void testFailNegativePrice()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "Brent", "Entire Place", "-10", "10", "10");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "Brent", "Entire Place", "-10", "10", "10"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        boolean falseTest = Arrays.equals(testArray, actualArray); 
        assertFalse(falseTest);
    }
    
    /**
     * Tests that item can't be added with a negative minimum night value
     */
    @Test
    public void testFailNegativeMinimumNight()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "Brent", "Entire Place", "10", "-10", "10");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "Brent", "Entire Place", "10", "-10", "10"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        boolean falseTest = Arrays.equals(testArray, actualArray); 
        assertFalse(falseTest);
    }
    
    /**
     * Tests that item can't be added with a negative availability value
     */
    @Test
    public void testFailNegativeAvailability365()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "Brent", "Entire Place", "10", "10", "-10");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "Brent", "Entire Place", "10", "10", "-10"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        boolean falseTest = Arrays.equals(testArray, actualArray); 
        assertFalse(falseTest);
    }
    
    /**
     * Tests that item can't be added with a non-integer price value.
     */
    @Test
    public void testFailNonIntPrice()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "Brent", "Entire Place", "Hello", "10", "10");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "Brent", "Entire Place", "Hello", "10", "10"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        boolean falseTest = Arrays.equals(testArray, actualArray); 
        assertFalse(falseTest);
    }
    
    /**
     * Tests that item can't be added with a non-integer minimum night value.
     */
    @Test
    public void testFailNonIntMinimumNight()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "Brent", "Entire Place", "10", "Hello", "10");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "Brent", "Entire Place", "10", "Hello", "10"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        boolean falseTest = Arrays.equals(testArray, actualArray); 
        assertFalse(falseTest);
    }
    
    /**
     * Tests that item can't be added with a non-integer availability value.
     */
    @Test
    public void testFailNonIntAvailability365()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "Brent", "Entire Place", "10", "10", "Hello");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "Brent", "Entire Place", "10", "10", "Hello"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        boolean falseTest = Arrays.equals(testArray, actualArray); 
        assertFalse(falseTest);
    }
    
    /**
     * Tests that item can't be added when there is no neighbourhood value.
     */
    @Test
    public void testFailNeighbourhood()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "-", "Entire Place", "10", "10", "10");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "-", "Entire Place", "10", "10", "10"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        boolean falseTest = Arrays.equals(testArray, actualArray); 
        assertFalse(falseTest);
    }
    
    /**
     * Tests that item can't be added if there is no room type value
     */
    @Test
    public void testFailRoom()
    {
        UploadPanel testPanel = new UploadPanel();
        testPanel.testInput("testProperty", "testHost", "Brent", "-", "10", "10", "10");
        
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = loader.load();
        AirbnbListing testProp = listings.get(listings.size()-1);
        
        String[] testArray = new String[] {"testProperty", "testHost", "Brent", "-", "10", "10", "10"};
        String[] actualArray = new String[] {testProp.getName(), testProp.getHost_name(), testProp.getNeighbourhood(), testProp.getRoom_type(), String.valueOf(testProp.getPrice()), String.valueOf(testProp.getMinimumNights()), String.valueOf(testProp.getAvailability365())};
        
        boolean falseTest = Arrays.equals(testArray, actualArray); 
        assertFalse(falseTest);
    }
}
 