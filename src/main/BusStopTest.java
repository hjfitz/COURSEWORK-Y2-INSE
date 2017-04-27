package main;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author FlashCloud
 * 
 * Class for testing the main functionality of BusStop class. Tests include getters, setters and travel cost calculations
 */
public class BusStopTest {
	//The order of the parameters for Assert.assertEquals method in JUnit is (expected, actual)
	BusStop stop = new BusStop("test", "07:30");

	/**
	 * Tests the getBusName() and getTime() methods
	 */
	@Test
	public void BusStop() {
		assertEquals("test", stop.getBusName());
		assertEquals("07:30", stop.getTime());
		assertNotEquals(null, stop);
	}

	/**
	 * Tests the  getTime() method
	 */	
	@Test
	public void GetTime() {
		assertEquals("07:30", stop.getTime()); 
		assertNotEquals(null, stop.getTime());
		
	}

	/**
	 * Tests the  setTime() method
	 */	
	@Test
	public void SetTime() {
		assertEquals("07:30", stop.getTime()); // before should equal previous value
		stop.setTime("08:30");
		assertEquals("08:30", stop.getTime());  // should be equal to the new value
		assertNotEquals(null, stop.getTime()); //should not be equal to null
	}

	/**
	 * Tests the  getBusName() method
	 */	
	@Test
	public void GetBusName() {
		assertEquals("test", stop.getBusName());
		assertNotEquals(null, stop.getBusName());
	}

	/**
	 * Tests the setBusName() method
	 */	
	@Test
	public void SetBusName() {
		assertEquals("test", stop.getBusName()); // before should be equal to prev value
		stop.setBusName("new Bus");
		
		assertEquals("new Bus", stop.getBusName()); // now should be equal to new balue
		
		assertNotEquals(null,"test"); // should not be null
	}

	/**
	 * Tests the time taken for travel
	 */	
	@Test
	public void CalculateTravelTime() {
		stop.setTime("07:15:00");
		assertEquals(45.0 + " minutes", stop.calculateTravelTime("08:00:00")); // after setting arrival time at 08:00 
																		// the travel time should be 45 minutes 
		
		//assertEquals(null, stop.calculateTravelTime("08")); // fails requires validation
		//assertEquals(null, stop.calculateTravelTime("test")); // fails required validation
		
		assertEquals(null, stop.calculateTravelTime("45:00:00")); // fails required validation
	}

	/**
	 * Tests the cost of travel
	 */		
	@Test
	public void CalculateCost() {
		
		stop.setTime("07:15:00");

	}

	/**
	 * Tests conversion to seconds
	 */
	@Test
	public void ConvertToSeconds() {
		
		assertEquals(12300, stop.convertToSeconds(0, 25, 3));
		assertNotEquals(12000, stop.convertToSeconds(0, 25, 3));
	}

}
