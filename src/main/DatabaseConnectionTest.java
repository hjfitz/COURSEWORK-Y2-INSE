package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

public class DatabaseConnectionTest {
	
	DatabaseConnection db = new DatabaseConnection();


	@Test
	public void Connect() {
		db.connect();
		assertEquals(false, db); // fails required a method to return if there is a connection
	}

	@Test
	public void CloseConnection() {
		db.connect();
		assertNotEquals(false, db); // fails required a method to return if there is a connection
		db.closeConnection();
		assertEquals(true, db); // fails required a method to return if there is a connection
	
	}

	@Test
	public void RunQuery() {
		db.connect();
		ArrayList<String>result = new ArrayList<String>();
		ResultSet test = db.runQuery("Select * from Stop");
		try {
			while(test.next()){
				
				String stop = test.getString("Stop_Name");
				
				result.add(stop);
			}
			assertEquals("Langstone Campus",result.get(0));
			assertEquals("Locks Way Road",result.get(1));
			assertEquals("LIDl",result.get(2));
			assertEquals("Fratton Station",result.get(3));
			assertEquals("CambrIDge Road",result.get(4));
			assertEquals("Winston Churchill Ave",result.get(5));
			
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Test
	public void RunInsert() {
		
		fail("Not yet implemented"); // leaving this blank, i dont want to update our database, need to test this on seperate one
	}



	@Test
	public void GetSpecificRoute() {
		db.connect();
		ArrayList<String>result = new ArrayList<String>();
		ResultSet test = db.getSpecificRoute("Locks Way Road", "12:30", true);
		try {
			while(test.next()){
				String time = test.getTime("Arrival_Time").toString();
				String stopName = test.getString("Stop_Name");
				
				result.add(time + " " + stopName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("14:30:00 Locks Way Road",result.get(0));
		assertEquals("15:45:00 Locks Way Road",result.get(1));
		assertEquals("17:00:00 Locks Way Road",result.get(2));
	
	}
	
	@Test
	public void GetPopRoute() {
		
	}
	
}
