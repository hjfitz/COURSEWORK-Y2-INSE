package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class BusStopTest {
	BusStop stop = new BusStop("test", "07:30");

	@Test
	public void BusStop() {
		BusStop stop2 = new BusStop("test", "07:30");
		assertEquals(stop.getBusName(),stop2.getBusName());
	}

	@Test
	public void GetTime() {
		assertEquals(stop.getTime(),"07:30"); 
	}

	@Test
	public void SetTime() {
		stop.setTime("08:30");
		assertEquals(stop.getTime(),"08:30");  // should be equal to the new value
		
		assertNotEquals(stop.getTime(),"07:30"); // should not be equal to the previous value
	}

	@Test
	public void GetBusName() {
		assertEquals(stop.getBusName(),"test");
	}

	@Test
	public void SetBusName() {
		stop.setBusName("new Bus");
		assertEquals(stop.getBusName(), "new Bus");
		
		assertNotEquals(stop.getBusName(),"test");
	}

	@Test
	public void CalculateTravelTime() {
		stop.setTime("07:15:00");
		assertEquals(45.0 + " minutes", stop.calculateTravelTime("08:00:00")); // after setting arrival time at 08:00 
																		// the travel time should be 45 minutes // fails
	}

	@Test
	public void CalculateCost() {
		stop.setTime("07:15:00");
		assertEquals(stop.calculateCost("08:00:00"), (0.6 * 45) + "p"); // fails
	}

	@Test
	public void ConvertToSeconds() {
		
		assertEquals(12300, stop.convertToSeconds(0, 25, 3));
		assertNotEquals(12000, stop.convertToSeconds(0, 25, 3));
	}

}
