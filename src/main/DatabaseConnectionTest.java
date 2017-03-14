package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.mysql.jdbc.Connection;

public class DatabaseConnectionTest {
	
	DatabaseConnection db = new DatabaseConnection();


	@Test
	public void Connect() {
		db.connect();
		assertNotEquals(null, db); // should not be null
		assertEquals(false,db.isClosed()); // should pass, as the connection is opened
		assertNotEquals(true,db.isClosed()); // should pass, as the connection is opened
	}

	@Test
	public void CloseConnection() {
		db.connect();
		assertEquals(false, db.isClosed()); // connection should be still open before closing
		db.closeConnection();
		assertEquals(true, db.isClosed()); // connection should now be closed
	
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
		ArrayList<String>result1 = new ArrayList<String>();
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
		
		ResultSet test1 = db.getSpecificRoute("Locks Way Road", "11:00", false);
		try {
			while(test1.next()){
				String time1 = test1.getTime("Arrival_Time").toString();
				String stopName1 = test1.getString("Stop_Name");
				result1.add(time1 + " " + stopName1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		assertEquals("07:15:00 Locks Way Road",result1.get(0));
		assertEquals("08:30:00 Locks Way Road",result1.get(1));
		assertEquals("09:45:00 Locks Way Road",result1.get(2));
		assertEquals("11:00:00 Locks Way Road",result1.get(3));
	
		
	}
	
	@Test
	public void GetPopRoute() {
		
	}
	
}
