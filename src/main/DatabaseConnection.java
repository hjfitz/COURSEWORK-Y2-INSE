package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

	private final String host = "jdbc:mysql://localhost/bustimes";
	private final String user = "root";
	private final String password = "root";
	private Connection connection;

	
	/***
	 * Connects to the database using specified host,user and password
	 * if the connection fails prints an exception
	 */
	public void connect() {
		try {
			connection = DriverManager.getConnection(host, user, password);
//			System.out.println("Successfully connected to database.");
//			System.out.println(connection);
		} catch (SQLException err) {
			System.out.println(err.getMessage());
		}
	}
	
	/***
	 * Closes the connection the database
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}
	
	/***
	 * Runs the specified query in the database
	 * @param qry  query to run in the database
	 * @return result set of the query
	 */
	public ResultSet runQuery(String qry) {
//		System.out.println("Attempting to run query: " + qry + "\n\n");
		try {
			PreparedStatement statement = connection.prepareStatement(qry);
			ResultSet rs = statement.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * Runs a insert query to the database
	 * @param qry specified insert query
	 */
	public void runInsert(String qry) {
//		System.out.println("Attempting to update database with: " + qry + "\n\n");
		try {
			PreparedStatement stmnt = connection.prepareStatement(qry);
			@SuppressWarnings("unused")
			int result = stmnt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public ResultSet getPopRoutes(String location, String hour, boolean departing) {
		String query = "select distinct * from Arrival_Times natural join Stop natural join Route natural join Popular where (Stop_Name = ?)";
		if (departing) {
			query += " AND ARRIVAL_TIME >= ? order by Arrival_time";
		} else {
			query += " AND ARRIVAL_TIME <= ? order by Arrival_time";
		}
		// create the java statement
		try {
//			System.out.println(query);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, location);
			statement.setString(2, "2017-02-15 " + hour);
//			System.out.println(statement);
			ResultSet rs = statement.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * Runs the query for specified route
	 * @param location specified bus stop
	 * @param hour specified hour of travel
	 * @param departing if arrive at that time or departing
	 * @return
	 */
	public ResultSet getSpecificRoute(String location, String hour, boolean departing) {
		String query = "select Arrival_Time, Stop_Name from Arrival_Times natural join Stop where (Stop_Name = ?)";
		if (departing) {
			query += " AND ARRIVAL_TIME >= ? AND ARRIVAL_TIME <= ? order by Arrival_time LIMIT 5";
		} else {
			query += " AND ARRIVAL_TIME <= ? order by Arrival_time";
		}
		// create the java statement
		try {
		//System.out.println(query);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, location);
			statement.setString(2, "2017-02-15 " + hour);
			if(departing){
				statement.setString(3, "2017-02-15 19:00:00");
			}
		System.out.println(statement);
			ResultSet rs = statement.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * Checks if the connection to the database is closed
	 * @return true if database is closed else false
	 */
	public boolean isClosed(){
		try {
			return connection.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}