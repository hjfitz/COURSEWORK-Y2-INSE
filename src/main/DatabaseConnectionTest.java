package main;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		ResultSet test = db.runQuery("Select * from Arrival_Times");
		try {
			while(test.next()){
				assertEquals(test.next(), db.runQuery("Select * from Arrival_Times").next()); // results sets should be equal
			}
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
	public void GetPopRoutes() {
		db.connect();
		ResultSet test = db.getPopRoutes("Locks Way Road", "12:30", true);
		try {
			while(test.next()){
				assertEquals(test.next(), db.getPopRoutes("Locks Way Road", "12:30", true).next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void GetSpecificRoute() {
		db.connect();
		ResultSet test = db.getSpecificRoute("Locks Way Road", "12:30", true);
		try {
			while(test.next()){
				assertEquals(test.next(), db.getSpecificRoute("Locks Way Road", "12:30", true).next()); // fails
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
