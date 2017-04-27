package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Window.Type;

/**
 * 
 * @author FlashCloud
 * Second user interface of the program used to filter buses
 */
@SuppressWarnings("serial")
public class Timetable extends JFrame {

	// create sql date and time types to compare in the query
	private static java.util.Date today = new java.util.Date();
	// private Time curTime = new Time(today.getTime());
	private static Date curDate = new Date(today.getTime());
	// TODO need to get a date for a week away and a month away

	// need to read this from a text file
	static String thisStop = "101";

	// prepare the query to get all of the bus routes that arrive at this stop
	private static String qryGetRouteNumsStart = "select distinct Route_ID, Route_Name from Arrival_Stop natural join Route natural join Arrival_Times where Stop_ID = ";
	private static String qryGetRouteNumsEnd = " and Arrival_Time >= '";// +
																		// curDate
																		// +
																		// "'";

	// prepare the query that gets all of the times. this will be nested in a
	// loop which goes through the IDs found in the previous query
	static String qryGetRoutes = "Select Arrival_time from Arrival_Stop natural join Route natural join Arrival_Times natural join Stop where Route_ID = ";

	// generated by windowBuilder
	private JPanel contentPane;
	private JTable table;
	JTextPane txtpnHints;
	private JTextField txtWeek;

	@SuppressWarnings("rawtypes")
	public Timetable() {
		setType(Type.UTILITY);
		setTitle("FlashCloud");
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 1248, 713);
		contentPane.add(panel);

		JLabel lblNextBus = new JLabel("Next Bus: ?? Minutes");
		lblNextBus.setHorizontalAlignment(SwingConstants.CENTER);

		DefaultTableModel ourTable = new DefaultTableModel(new Object[][] { { null, null }, },
				new String[] { "Route Name", "Times" });

		String[] data = new String[2];
		data[0] = "Route Name";
		data[1] = "Times";
		ourTable.addRow(data);

		JButton btnDay = new JButton("Day");
		btnDay.addMouseListener(new MouseAdapter() {
			@Override
			//get the times for the bus, within the next 24 hours
			public void mouseClicked(MouseEvent arg0) {
				getTimes("1 DAY", ourTable);

			}
		});
	
		btnDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtpnHints.setText("Set your view to daily");
				
			}
		});

		JButton btnWeek = new JButton("Week");
		btnWeek.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//get the times for the bus, within the next week
				getTimes("1 WEEK", ourTable);
			}
		});

		btnWeek.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtpnHints.setText("Set your view to weekly");
				
			}
		});

		JButton btnMonth = new JButton("Month");
		btnMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getTimes("1 MONTH", ourTable);
			}
		});

		
		btnMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtpnHints.setText("Set your view to monthly");
				
			}
		});
		
		
		JComboBox cmbRoutes = new JComboBox();
		cmbRoutes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtpnHints.setText("Here you can change your current viewed stop");
				
			}
		});

		JButton btnTemp = new JButton("All Times");


		// start adding code here

		btnTemp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getTimes("", ourTable);

			}
		});

		JButton btnRefresh = new JButton("refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				changeRouteNo(cmbRoutes);
				getTimes("1 DAY", ourTable);
			}
		});
		getStops(cmbRoutes);
		

		JScrollPane scrollPane = new JScrollPane();
		
		txtpnHints = new JTextPane();
		txtpnHints.setFont(new Font("Arial", Font.PLAIN, 23));
		
		txtpnHints.setText("Hints: Welcome to Flashcloud, here you can view current stop's timetable, filter your searches by choosing a daily"
				+ ",weekly or monthly timetable");

		JButton btnSearch = new JButton("Search");
		
		/**
		 * show the main form when we click the rigt button
		 */
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main mainForm = new Main();
				mainForm.setVisible(true);

			}
		});

		/**
		 * show the parser form when we click the button
		 */
		JButton btnUpdateRoutes = new JButton("Update Routes");
		btnUpdateRoutes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ParseForm parser = new ParseForm();
				parser.setVisible(true);
			}
		});
		
		JComboBox cmbStops = new JComboBox();
		
		JButton btnSearchByRoute = new JButton("search by stop");
		btnSearchByRoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//get the routename from the combo box and search by that 
				String routeName = cmbStops.getSelectedItem().toString();
				getByRoute(routeName, ourTable);
				
			}
		});
		
		JButton btnViewForSpecific = new JButton("View for specific week");
		btnViewForSpecific.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//get the week from the text field an search by that
					getWeek(txtWeek.getText(), ourTable);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		
		txtWeek = new JTextField();
		txtWeek.setColumns(10);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtpnHints, GroupLayout.DEFAULT_SIZE, 1238, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNextBus, GroupLayout.DEFAULT_SIZE, 1228, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(12)
							.addComponent(cmbRoutes, 0, 1075, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1228, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnUpdateRoutes)
									.addGap(116)
									.addComponent(btnViewForSpecific)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtWeek, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnSearch)
									.addGap(655)
									.addComponent(btnDay, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
									.addGap(7)
									.addComponent(btnWeek, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
									.addGap(3)
									.addComponent(btnMonth, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnTemp, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(cmbStops, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSearchByRoute)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbRoutes, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbStops, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearchByRoute))
					.addGap(8)
					.addComponent(lblNextBus, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(19)
							.addComponent(btnUpdateRoutes)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnTemp, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnDay, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnSearch))
								.addComponent(btnWeek, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnMonth, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnViewForSpecific)
								.addComponent(txtWeek, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
					.addComponent(txtpnHints, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(ourTable);
		panel.setLayout(gl_panel);
		getRoutes(cmbStops);
	}

	// show the window when this file is run
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//create a new timetable form, and show it
					Timetable frame = new Timetable();
					Dimension d = new Dimension(1366,768);
					frame.setSize(d);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	
	/**
	 * given a date of the form yyyy-MM-dd, we format the day to make it look nicer
	 * the date should be returned in the form 'Mon 08-Jan'
	 * Uses simpleDateFormat to get a java.util.date
	 * @param date the string to be formatted
	 * @return nicely formatted date.
	 * @throws ParseException in case the pattern passed doesn't match correctly.
	 */
	public static String parseDate(String date) throws ParseException {
		
		//create a format
		SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd");
		
		//parse the date and apply the pattern, store in var
		java.util.Date parsedDate = iso8601.parse(date);
		iso8601.applyPattern("EEE dd-MMM");
		String day = iso8601.format(parsedDate);
		return day;
	}

	/**
	 * nulls the table on the main form
	 * searches the database for times, in a given interval
	 * adds them to the table on the form
	 * @param constraints constraints, if any, to be put on the SQL query
	 * @param routeTable the table that we wish to add new data to
	 */
	@SuppressWarnings("unused")
	public static void getTimes(String constraints, DefaultTableModel routeTable) {
		
		//null the table
		routeTable.setRowCount(0);
		
		//connect to the database
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		
		//create a default query, run that through the database
		String qryGetRouteNums = qryGetRouteNumsStart + thisStop;
		ResultSet routeNumbers = dbConn.runQuery(qryGetRouteNums);
		
		//initialise variables
		String date = "";
		try 
		{
			//go through the returned values in the database
			while (routeNumbers.next()) 
			{
				//initialise variables to store information
				String[] routeInfo = new String[2];
				
				//store the name of the route
				routeInfo[0] = routeNumbers.getString("Route_Name");

				String qryGetTimes = qryGetRoutes 
						           + routeNumbers.getInt("Route_ID")
						           + " and Stop_ID = " + thisStop
						           + qryGetRouteNumsEnd + curDate + "'";
				
				//if there's any constraints, add them
				if (constraints != "") qryGetTimes += "and Arrival_Time <= (SELECT '" + curDate + "' + INTERVAL " + constraints + ")";
				
				//order the results, for the user
				qryGetTimes += " order by Arrival_Time ";
				
				//having created the query, run it
				ResultSet times = dbConn.runQuery(qryGetTimes);
				
				// loop through the results
				while (times.next())
				{
					//attempt to parse the time we get, 
					//this shouldn't crash because we assume the date from mysql to be always of the same format.
					try {
						date = parseDate(times.getDate("Arrival_Time").toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					//format the output with the date, and the arrival time
					String out = date + " " + times.getTime("Arrival_Time").toString();
					routeInfo[1] = out;
					
					//add out array to the table
					routeTable.addRow(routeInfo);
					routeInfo[0] = "";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//don't leave many open connections.
		dbConn.closeConnection();
	}
	
	/**
	 * get the information for a selected route
	 * put all of the info on the table on the form
	 * @param routeName name of route to search for
	 * @param routeTable table to put results
	 */
	@SuppressWarnings("unused")
	public static void getByRoute(String routeName, DefaultTableModel routeTable) {
		
		// null the table
		routeTable.setRowCount(0);
		
		//connect to the database
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		
		//prepare a query, run it and store the results in arrivalTimes
		String query = "SELECT distinct Stop_Name, Arrival_Time from Arrival_Stop natural join Arrival_Times natural join Stop natual join Route where Route_Name = '" + routeName + "'";
		ResultSet arrivalTimes = dbConn.runQuery(query);
		String date = "";
		try {
			//go through the results, and store the information in an array (stopInfo)
			//we use the array to put the information on to the table
			while (arrivalTimes.next()) 
			{
				//create a store for the information
				String stopInfo[] = new String[2];
				
				//store the name in [0]
				stopInfo[0] = arrivalTimes.getString("Stop_Name");
				
				//attempt to parse the date
				try {
					date = parseDate(arrivalTimes.getDate("Arrival_Time").toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//add information to th array, and add that array to the table
				stopInfo[1] = date + " " + arrivalTimes.getTime("Arrival_Time").toString();
				routeTable.addRow(stopInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//don't leave any open connections
		dbConn.closeConnection();
	}

	/**
	 * changes the stop that the search functions 'all times, month, day' 
	 * so that we may search for a different stop. 
	 * This is invoked when the user changes the stop in the combo box
	 * @param cmb combo box that contains the route to change
	 */
	@SuppressWarnings("rawtypes")
	public static void changeRouteNo(JComboBox cmb) {
		
		//prepare the query, connect to the database and 
		String qry = "Select distinct Stop_ID from Stop where Stop_Name = '" + cmb.getSelectedItem().toString() + "'";
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		ResultSet stopID = dbConn.runQuery(qry);
		
		String stop = thisStop;

		//we want to change the stop number to the one of the selected item
		//we've got the item in the result set, because we search where name=comboBox
		//therefore, we get one result, and set stop to the id. we loop, because one item is returned.
		try {
			while (stopID.next()) {
				stop = stopID.getString("Stop_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConn.closeConnection();
		//set global stop id to the stop.
		thisStop = stop;

	}

	/**
	 * gets all of the routes from the database, and fills a combo box with their names
	 * @param box the combo box to be populated.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void getRoutes(JComboBox box) {
		//prepare query
		String query = "Select distinct * from Route";
		
		//connect to database
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		
		//get route names from database
		ResultSet routeNames = dbConn.runQuery(query);
		try {
			
			//go through results and add item to combo box
			while (routeNames.next()) {
				String route = routeNames.getString("Route_Name");
				box.addItem(route);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//don't leave any open connections to the database
		//to avoid poor performance
		dbConn.closeConnection();
	}

	/**
	 * gets the stop names from the database, and their ids
	 * add them to a combo box
	 * if the stop ID is equal to the set ID, set the selected item
	 * @param box
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void getStops(JComboBox box) {
		
		//prepare a query
		String qry = "Select distinct * from Stop";
		
		//set the variable for the current stop name
		String curStopName = "";
		
		//connect to the database
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		
		//get all of the stop names from the database
		ResultSet stopNames = dbConn.runQuery(qry);
		try {
			
			//go through the results and add them to the combo box
			while (stopNames.next()) {
				//cast the stop id to a string for comparison
				String curStopID = "" + stopNames.getInt("Stop_ID");
				
				//get the stop name from the result set and add to the combo box
				String curStop = stopNames.getString("Stop_Name");
				box.addItem(curStop);
				
				//set the name of the current stop to the variable
				if (curStopID.equals(thisStop)) curStopName = curStop;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//close the connection 
		dbConn.closeConnection();
		
		//because we found the stop name that equals the id setting
		//set the combo box of stop names to this stop.
		box.setSelectedItem(curStopName);
	}
	
	
	/**
	 * gets the bus times for a given week long period
	 * @param weekStart begninning of the week
	 * @param routeTable the table to add the information to
	 * @throws ParseException exception thrown in case the date doesn't get parsed correctly
	 */
	@SuppressWarnings("deprecation")
	public static void getWeek(String weekStart, DefaultTableModel routeTable) throws ParseException {
		//null the table
		routeTable.setRowCount(0);
		
		// set up a formatter for the time
		String weekFormat = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(weekFormat);
		java.util.Date date = new java.util.Date();
		date = df.parse(weekStart);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//get the time a week from now
		cal.add(Calendar.DATE, 7);

		//format the date correctly for use in the sql query
		String nextDate = "2017-" + (cal.getTime().getMonth() + 1) + "-" + cal.getTime().getDate();
		
		//connect to the database
		DatabaseConnection dbconn = new DatabaseConnection();
		dbconn.connect();
		
		//prepare the query
		String query = "Select distinct * from Route natural join Stop natural join Arrival_Times natural join Arrival_Stop where Stop_ID = " + thisStop;
		query += " and Arrival_Time >= '" + weekStart
		      + "' and Arrival_Time <= '" + nextDate + "'";
		
		//get the times from the database
		ResultSet stops = dbconn.runQuery(query);
		try 
		{
			//go through the result set returned
			while (stops.next()) {
				//retrieve the data from the list
				String name = stops.getString("Route_Name");
				Date dateStop = stops.getDate("Arrival_Time");
				String time = stops.getTime("Arrival_Time").toString();
				//format the strings
				String parsedDate = parseDate(dateStop.toString());
				String timeCol = parsedDate + " " + time;
				//create an array to add to the table on the form 
				String[] data = new String[2];
				data[0] = name;
				data[1] = timeCol;
				
				//add to the form
				routeTable.addRow(data);
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		dbconn.closeConnection();
		
	}
}
