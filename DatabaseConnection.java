

import java.sql.*;


public class DatabaseConnection {
    
	private final String host = "jdbc:mysql://localhost/bustimes";
	private final String user = "root";
	private final String password = "root";
	private Connection connection;    
        
	public void connect() {
            try {
                connection = DriverManager.getConnection(host, user, password);
                System.out.println("Successfully connected");
            } catch(SQLException err){
                System.out.println(err.getMessage());
            }
	}
	
        public void closeConnection() {
		try {
                    connection.close();
		} catch(SQLException err){
                    err.printStackTrace();
            }
	}
        
        
        public ResultSet getSpecificRoute(String table, String from, String to){
            
          String query = "select * from bustimes.Arrival_Times natural join Stop order by Arrival_Time";
          
         //where Stop_Name = ? || Stop_Name = ?"
          
          // create the java statement
         try{
            
             PreparedStatement statement = connection.prepareStatement(query);    
             //statement.setString(1, from);
             //statement.setString(2, to);   
             ResultSet rs = statement.executeQuery();
             
            return rs;
            
         
         }catch(SQLException e){
             e.printStackTrace();
         }
         
            return null;
        }
        
 
       
}
