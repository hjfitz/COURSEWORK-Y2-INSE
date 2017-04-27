package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author FlashCloud
 * 
 * Main class of program which is the 1st user interface form opened. Allows the user to search for buses from different bus stops and times. 
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtHour;
	private JLabel lbl_estimate;
	private JToggleButton btn_font;
	private JTextArea txtHints;
	private JTable table_2;
	private JTextField txtMinute;
	private JLabel lblSorryNoRoutes;
	private String currentTime;
	private SimpleDateFormat format;
	private ImageIcon tick = new ImageIcon("img/tick.png");
	private ImageIcon cross = new ImageIcon("img/cross.png");
	private JLabel label_1,label_2,label_3;
	private JScrollPane scrollPane;
	

	/**
	 * Launch the application. Run with the class
	 * Attempts to a new Main object called 'frame' and calls the method .setVisible
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main frame = new Main();
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
	 * Create the frame.
	 * The vast majority of the code in this constructor is used for creating components in the field 
	 * or adding event listeners.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Main() {
		setTitle("FlashCloud");
		setResizable(false);
		currentTime = "";
		
		tick = scaleIcon(tick);
		cross = scaleIcon(cross);
		
		
		
		//create a simple data format for universal use
		format = new SimpleDateFormat("HH:mm:ss");
		
		//connect to the database to fill the items on the field
		DatabaseConnection conn = new DatabaseConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btn_font = new JToggleButton("Increase Font Size");
		btn_font.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_font.setForeground(new Color(0, 0, 128));
		btn_font.setBackground(new Color(100, 149, 237));
		btn_font.setToolTipText("Tap to increase / decrease font size");

		btn_font.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtHints.setText("Tap to increase / decrease font size");

			}
		});
		btn_font.setBounds(1116, 11, 186, 47);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(10, 6, 1340, 717);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblSearchForA = new JLabel("Search for a specific route");
		lblSearchForA.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchForA.setForeground(new Color(0, 0, 128));
		lblSearchForA.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblSearchForA.setBounds(10, 5, 1320, 55);
		panel.add(lblSearchForA);

		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		comboBox.setForeground(Color.BLACK);
		comboBox.setToolTipText("");
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Locks Way Road", "Lidl", "Fratton Station",
				"Cambridge Road", "Winston Churchill Ave" }));

		comboBox.setBounds(507, 71, 314, 37);

		panel.add(comboBox);

		JComboBox combo_Arrive = new JComboBox();
		combo_Arrive.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		combo_Arrive.setForeground(new Color(0, 0, 128));
		combo_Arrive.setModel(new DefaultComboBoxModel(new String[] { "Locks Way Road", "Lidl", "Fratton Station",
				"Cambridge Road", "Winston Churchill Ave" }));
		combo_Arrive.setSelectedIndex(2);
		combo_Arrive.setBounds(507, 136, 318, 37);
		panel.add(combo_Arrive);

		JLabel lblTo = new JLabel("Depart from");
		lblTo.setForeground(new Color(0, 0, 128));
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTo.setBounds(507, 54, 108, 16);
		panel.add(lblTo);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		comboBox_2.setForeground(new Color(0, 0, 128));
		comboBox_2.setBackground(new Color(255, 255, 255));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "Arrive", "Depart" }));
		comboBox_2.setSelectedIndex(1);
		comboBox_2.setBounds(542, 184, 233, 25);
		panel.add(comboBox_2);

		JLabel lblMostPopularRoutes = new JLabel("Most popular routes");
		lblMostPopularRoutes.setForeground(new Color(0, 0, 128));
		lblMostPopularRoutes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMostPopularRoutes.setHorizontalAlignment(SwingConstants.CENTER);
		lblMostPopularRoutes.setBounds(317, 485, 765, 16);

		panel.add(lblMostPopularRoutes);

		txtHints = new JTextArea();
		txtHints.setLineWrap(true);
		txtHints.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		txtHints.setBounds(178, 626, 1062, 80);
		panel.add(txtHints);
		txtHints.setText("Welcome to FlashClould! Tap [Search] to display all buses coming here next.");

		txtHour = new JTextField();
		txtHour.setName("hour");
		txtHour.setHorizontalAlignment(SwingConstants.CENTER);
		txtHour.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtHour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtHints.setText("Enter the time you want to arrive / depart at");
			}
		});
		Date date = new Date(); // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new
																// calendar
																// instance
		calendar.setTime(date); // assigns calendar to given date
		// gets hour in 24h format
		DateFormat formatter = new SimpleDateFormat("hh:mm");
		txtHour.setText(Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)));
		txtHour.setBounds(544, 220, 71, 53);
		panel.add(txtHour);
		txtHour.setColumns(2);

		JLabel lblArriveAt = new JLabel("Arrive at\n");
		lblArriveAt.setForeground(new Color(0, 0, 128));
		lblArriveAt.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblArriveAt.setBounds(507, 119, 88, 16);
		panel.add(lblArriveAt);

		JButton btn_Search = new JButton("Search");
		btn_Search.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btn_Search.setForeground(new Color(0, 0, 128));
		btn_Search.setBackground(new Color(100, 149, 237));
		btn_Search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				txtHints.setText("Tap [Search] to display all buses coming here next.");
			}
		});
		btn_Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fixedHour = 0;
				int fixedMin = 0;
				
				//find out as to whether we're departing or not.
				Boolean departing = (comboBox_2.getSelectedItem().toString() == "Depart");
				
				//open the connection to the database
				conn.connect();
				
				//get information for the departing and arriving stops from the form
				String from = comboBox.getSelectedItem().toString();
				String to = combo_Arrive.getSelectedItem().toString();
				
				//format the hours and minutes depending on whether we're departing or not
				if (departing) {
					fixedHour = Integer.parseInt(txtHour.getText()) - 1;
					fixedMin = Integer.parseInt(txtMinute.getText());
				} else {
					fixedHour = Integer.parseInt(txtHour.getText());
					fixedMin = Integer.parseInt(txtMinute.getText()) + 15;
				}
				
				//set up the time, for use in search
				String hour = fixedHour + ":" + fixedMin;

				//call search with the main jTable, for the results.
				search(from, to, hour, departing, table_2);

			}
		});

		btn_Search.setBounds(581, 303, 164, 37);
		panel.add(btn_Search);

		JLabel lblHints = new JLabel("Hints");
		lblHints.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHints.setBounds(178, 605, 46, 14);
		panel.add(lblHints);

		/**
		 * if the button 'change font' is clicked, create a list of elements 
		 * and call changeFontSize()
		 */
		btn_font.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//create a list of elements which need their font sizes changed
				JComponent elementList[] = { 
						lbl_estimate,
						lblHints,
						lblSearchForA, 
						lblTo,
						comboBox, 
						lblArriveAt,
						combo_Arrive,
						comboBox_2,
						txtHour, 
						btn_Search, 
						lblMostPopularRoutes
						};
				
				//find out if the user requires the font to be enlarged or not.
				Boolean enlargeFont = btn_font.isSelected();
				
				//change the font size
				changeFontSize(enlargeFont, elementList);

			}
		});

		panel.add(btn_font);
		lbl_estimate = new JLabel("Estimated Time until next bus: ");
		lbl_estimate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_estimate.setVisible(false);
		lbl_estimate.setForeground(new Color(0, 0, 128));
		lbl_estimate.setBounds(37, 461, 1330, 25);
		panel.add(lbl_estimate);

		JButton btnViewTimetables = new JButton("View timetables");
		btnViewTimetables.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnViewTimetables.setForeground(new Color(0, 0, 128));
		btnViewTimetables.setBackground(new Color(100, 149, 237));
		btnViewTimetables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Timetable timeForm = new Timetable();
				timeForm.setVisible(true);
			}
		});
		btnViewTimetables.setBounds(1116, 86, 186, 55);
		panel.add(btnViewTimetables);

		JTextPane txtpnPopRoutes = new JTextPane();
		txtpnPopRoutes.setBounds(178, 503, 1058, 91);
		panel.add(txtpnPopRoutes);

		getOrderedPopularRoutes(txtpnPopRoutes);

		txtMinute = new JTextField();
		txtMinute.setName("minute");
		txtMinute.setHorizontalAlignment(SwingConstants.CENTER);
		txtMinute.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		String minute = Integer.toString(calendar.get(Calendar.MINUTE));
		if (Integer.parseInt(minute) < 10) {
			minute = "0" + minute;
		}
		txtMinute.setText(minute);
		txtMinute.setColumns(2);
		txtMinute.setBounds(712, 220, 71, 53);
		panel.add(txtMinute);

		currentTime += txtHour.getText() + ":" + txtMinute.getText() + ":" + "00";

		JLabel label = new JLabel(":");
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label.setBounds(656, 226, 12, 37);
		panel.add(label);
		
		label_1 = new JLabel("");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 5));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(545, 85, 16, 16);
		panel.add(label_1);
		
		label_2 = new JLabel("");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 5));
		label_2.setBounds(545, 140, 16, 16);
		panel.add(label_2);
		
		label_3 = new JLabel("");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 5));
		label_3.setBounds(506, 219, 16, 16);
		panel.add(label_3);
		
		JLabel[] labels = new JLabel[3];
		labels[0] = label_1;
		labels[1] = label_2;
		labels[2] = label_3;
		
		scrollPane = new JScrollPane();
				scrollPane.setBounds(178, 351, 1059, 99);
				panel.add(scrollPane);
				
						table_2 = new JTable();
						table_2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
						
								scrollPane.setViewportView(table_2);
								
										JLabel lblNewLabel = new JLabel("New label");
										scrollPane.setColumnHeaderView(lblNewLabel);
										
												lblSorryNoRoutes = new JLabel("Sorry, no routes have been found");
												lblSorryNoRoutes.setHorizontalAlignment(SwingConstants.CENTER);
												lblSorryNoRoutes.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
												lblSorryNoRoutes.setBounds(35, 393, 1320, 37);
												panel.add(lblSorryNoRoutes);
		
		/**
		 * add event listeners to both combo boxes, to ensure that we don't search from and to on the same list.
		 */
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label_1.setVisible(true);
				label_2.setVisible(true);
				disableSearch(comboBox, combo_Arrive, btn_Search);
			}
		});

		/**
		 * add event listeners to both combo boxes, to ensure that we don't search from and to on the same list.
		 */
		combo_Arrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label_1.setVisible(true);
				label_2.setVisible(true);
				disableSearch(comboBox, combo_Arrive, btn_Search);
			}

		});

		/**
		 * Add event listener to text box, listener whenever something happens, check if the input is valid.
		 */
		txtHour.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				validInput(txtHour, btn_Search);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validInput(txtHour, btn_Search);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

		});


		/**
		 * Add event listener to text box, listener whenever something happens, check if the input is valid.
		 */
		txtMinute.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				validInput(txtMinute, btn_Search);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validInput(txtMinute, btn_Search);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

		});
		
		loadIcons(labels);

	}

	
	/**
	 * Searches for all of the bus times, given the time, where we want to depart from, and where we want to go to
	 * @param from The stop that we're leaving from 
	 * @param to Where we wish to go to
	 * @param hour The time that we should search 
	 * @param departing if we're departing or not 
	 * @param ourTable the table on the form that we want to update
	 * This method firstly creates a connection to the database that we're using.
	 * then it queries the database using the method 'getSpecificRoute()', which returns route information.
	 * given that route information, table 'Popular' is updated, with its hit count increased.
	 * The method then iterates over the result set and adds the information to an arraylist of BusTimes
	 * We use the getSpecificRoute() method from DatabaseConnection to find all of the times for the destination
	 * A new defaulttablemodel is created, and the information that's parsed (to look better and more informative)
	 * The model of the parameter ourTable is then set to that table.
	 * 
	 */
	public void search(String from, String to, String hour, Boolean departing, JTable ourTable) {
		
		//create a new database connection and connect
		DatabaseConnection conn = new DatabaseConnection();
		conn.connect();
		
		// create lists to store the returned busses from the select queries.
		// these are used later on to populate a new DefaultTableModel which 'ourTable' will be set to
		ArrayList<BusStop> fromStop = new ArrayList<BusStop>();
		ArrayList<BusStop> toStop = new ArrayList<BusStop>();
		
		// perform a search for a specific route, and popular router given the parameters
		ResultSet rs1 = conn.getSpecificRoute(from, hour, departing);
		ResultSet popRoutes = conn.getPopRoutes(from, hour, departing);

		// try to loop through popular routes and update the hits
		// this chunk of code uses the same routes that our search query may return.
		// because these route IDs are returned, and possibly returned often - they may be of use to some users
		// therefore, there is a table for them, with a 'count' of how often they are searched.
		// this try-catch chunk of code updates the routes (increasing their hit count) returned in the search
		try {
			while (popRoutes.next()) {
				// get attributes for a future insert query
				int curID = popRoutes.getInt("Route_ID");
				int hitCount = popRoutes.getInt("Route_Count");
				
				//increase the hit count
				int newHits = hitCount + 1;
				
				//create a new query and run it. No need for sanitization because there should be no user input.
				String query = "update Popular set Route_Count=" + newHits + " where Route_ID=" + curID;
				conn.runInsert(query);
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		
		//Create rs2 outside of the try-while-if scope, for use later on
		ResultSet rs2;
		
		try 
		{
			//loop over the result set and populate fromStop with new BusStop items
			while (rs1.next()) 
			{
				//get the attributes from the query
				String time     = rs1.getTime("Arrival_Time").toString();
				String stopName = rs1.getString("Stop_Name");
				
				//make a new bus stop with the attributes gotten two lines ago
				fromStop.add(new BusStop(stopName, time));
			}
			
			// if we want to see which times are departing,
			// and the stop we're leaving from isn't empty:
			if (departing && !fromStop.isEmpty()) {
				// query the database based on where we want to go, and the earliest time that's been returned.
				rs2 = conn.getSpecificRoute(to, fromStop.get(0).getTime(), departing);

				//format the times for the form
				Date currentT   = format.parse(currentTime);
				Date arriveTime = format.parse(fromStop.get(0)
							                           .getTime());
				
				// get the time between the arrival and current time.
				String diff = timeDifference(currentT, arriveTime);
				lbl_estimate.setText(diff);
			} else {
				// query the database based on where we want to go, and the time passed.
				rs2 = conn.getSpecificRoute(to, hour, departing);
			}

			
			// if we can't find any stops, notify the user by hiding the panel of times
			// and showing a label with 'no routes found'. else, hide that label and show the panel.
			if (fromStop.isEmpty()) {
				scrollPane.setVisible(false);
				lblSorryNoRoutes.setVisible(true);
			} else {
				scrollPane.setVisible(true);
				lblSorryNoRoutes.setVisible(false);
			}
			
			
			// like we did earlier, loop through the result set, creating busStop objects and adding them to an arraylist
			while (rs2.next()) 
			{
				String time     = rs2.getTime("Arrival_Time").toString();
				String stopName = rs2.getString("Stop_Name");
				toStop.add(new BusStop(stopName, time));
			}
			
			//create a new defaulttablemodel. This will be the new view for the jtable
			DefaultTableModel model = new DefaultTableModel(
					new String[] {
							"Depart at",
							"Arrive at",
							"From", 
							"To", 
							"Travel time", 
							"Estimated Price" 
							}, 0);
			
			// loop through the returned bus times
			for (int i = 0; i < toStop.size(); i++) 
			{
				// create a string travel, the total time between the item in fromStop and it's corresponding 
				// entry in toStop
				String travel = fromStop.get(i)
										.calculateTravelTime(toStop.get(i)
												                   .getTime()
												                   );
				
				//same as above, but we get the *cost*
				String price = fromStop.get(i)
									   .calculateCost(fromStop.get(i)
											   .getTravelTime());
				
				
				//split the times in to hours and minutes (by splitting by ':'
				String[] timeFrom = fromStop.get(i)
						                    .getTime()
						                    .split(":");
				
				
				String[] timeTo = toStop.get(i)
						                .getTime()
						                .split(":");
				
				
				if (!travel.equals("past")) {
					//add a new row to the table, which will soon be available on screen
					model.addRow(new Object[] { 
							timeFrom[0] + ":" + timeFrom[1], timeTo[0] + ":" + timeTo[1],
							fromStop.get(i).getBusName(),
							toStop.get(i).getBusName(),
							travel,
							price 
							});
					ourTable.setModel(model);
				}
			}

		// catch the many errors that we've encountered so many times.
		} catch (SQLException  | ParseException | IndexOutOfBoundsException e1) {
			e1.printStackTrace();
		}
		
		table_2.addMouseListener(new MouseAdapter(){
			DefaultTableModel model = (DefaultTableModel) table_2.getModel();
			@Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {
		            final JTable target = (JTable)e.getSource();
		            final int row = target.getSelectedRow();
		            
		            String[] item = model.getDataVector().elementAt(row).toString().split(",");
		            String[] departTime = item[0].split("\\[");
		            String routeInformation = "Depart from" + item[2] + " at " + departTime[1] ;
		            routeInformation += " Arrive at" + item[3] + " at" + item[1];
		            
		            lbl_estimate.setText(routeInformation);
		            lbl_estimate.setVisible(true);
		        }
		    }
			
			
		});
	}

	
	/**
	 * Gets the popular routes from the database, in order.
	 * formats the names on new lines, and changes parameter text to these route names
	 * @param popPane JTextPane to change the text of
	 */
	public void getOrderedPopularRoutes(JTextPane popPane) {
		
		//create a new database connection
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		
		// design a query. Get the names of the routes, sorted by the most popular
		// natural join popular and route to get the route name
		String query = "SELECT Route_Name FROM Popular NATURAL JOIN Route ORDER BY Route_Count";
		
		//run the query and save in to popRoutes
		ResultSet popRoutes = dbConn.runQuery(query);
		
		//initialise orderRoutes for use outside of scope
		String orderRoutes = "";
		try {
			// iterate through the result set, and set up orderRoutes 
			// with the name of each route on each line.
			while (popRoutes.next()) 
			{
				//get the name
				String routeName = popRoutes.getString("Route_Name");
				
				//append the name to orderRoutes and add a newline
				orderRoutes += routeName + "\n";
			}
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
		
		//change the text of popPane (parameter) to the popular routes on new lines.
		popPane.setText(orderRoutes);
	}

	/**
	 * loops through a list of JComponents and changes their font to a larger or smaller one, depending on the parameter.
	 * font is set to Arial +/- the current font size.
	 * @param enlarge whether the font should be enlarged or made smaller
	 * @param elementList a list of elements to change the font an font size
	 */
	public void changeFontSize(Boolean enlarge, JComponent[] elementList) {
		if (enlarge) {
			
			for (JComponent element : elementList) 
			{
				//set font to Arial at a size bigger than the current size
				element.setFont(new Font("Arial", Font.PLAIN, element.getFont().getSize() + 5));
				
				//change the context on the button on the form to something more suited.
				btn_font.setText("Decrease Font Size");
			}
		} else {
			
			for (JComponent element : elementList) 
			{
				//set font to Arial at size smaller than the current size
				element.setFont(new Font("Arial", Font.PLAIN, element.getFont().getSize() - 5));
				
				//change the context on the button on the form to something more suited.
				btn_font.setText("Increase Font Size");
			}
		}
	}

	
	/**
	 * Given two items to search for, if they're the same then disable the search button.
	 * @param comboBox the combo box that contains the departing route name
	 * @param combo_Arrive the combo box that contains the arriving route name
	 * @param btn_Search the button to press to invoke a search method
	 */
	public void disableSearch(JComboBox<String> comboBox, JComboBox<String> combo_Arrive, JButton btn_Search) {
		
		//get info from combo boxes
		String from = comboBox.getSelectedItem()
				              .toString();
		
		String to   = combo_Arrive.getSelectedItem()
				                  .toString();
		
		//if the items in the combo box, disable the search button. otherwise enable it!
		if (from.equals(to)) {
			btn_Search.setEnabled(false);
			txtHour.setEnabled(false);
			txtMinute.setEnabled(false);
			label_1.setIcon(cross);
			label_2.setIcon(cross);
			
			
		} else {
			btn_Search.setEnabled(true);
			label_1.setIcon(tick);
			label_2.setIcon(tick);
			txtHour.setEnabled(true);
			txtMinute.setEnabled(true);
		}
	}

	
	/**
	 * if the text field contains a value that is either an invalid minute or hour, disable the search button 
	 * @param txt a text field to validate
	 * @param btn_Search a button that is disabled if the txt is invalid.
	 */
	public void validInput(JTextField txt, JButton btn_Search) {
		//get the text from the text field
		String text = txt.getText();
		label_3.setVisible(true);
		
		//set the button to disabled if the text field is empty
		if (text.isEmpty()) {
			btn_Search.setEnabled(false);
			label_3.setIcon(cross);
		} else {
			
			try {
				//try to get an integer from the text field.
				int val = Integer.parseInt(text);
				
				//if we've got an hour field, check properly
				if (txt.getName().equals("hour")) {
					
					//if we've got an invalid hour, disable the button
					if (val > 24 || val < 0) {
						btn_Search.setEnabled(false);
						label_3.setIcon(cross);
					} else {
						btn_Search.setEnabled(true);
						label_3.setIcon(tick);
					}
					
				//if we've got a minute field, check properly.
				} else if (txt.getName().equals("minute")) {
					
					//if we've got an invalid minute, disable the button
					if (val > 59 || val < 0) {
						btn_Search.setEnabled(false);
						label_3.setIcon(cross);
					} else {
						btn_Search.setEnabled(true);
						label_3.setIcon(tick);
					}
				}
				//catch an error when trying to cast the text to an int.
				//if we've got an error, there's an erroneous input and therefore the button should be disabled.
			} catch (NumberFormatException e) {
				label_3.setIcon(cross);
				btn_Search.setEnabled(false);
			}
		}
	}

	
	/**
	 * find the difference between the times as parameters
	 * return a formatted a formatted string with relevant information 
	 * if there's a difference in time, we show a label. else, it's hidden.
	 * 
	 * @param time1 the beginning time, departing time to be compared
	 * @param time2 the beginning time, departing time to be compared
	 * @return estimated time as formatted string for putting on label
	 */
	public String timeDifference(Date time1, Date time2) {
		
		//instantiate the return value
		String estimateVal = "";
		
		//find difference in times, given as parameters
		long timeDiff = time2.getTime() - time1.getTime();
		
		//find the time in hours
		long estimateH = (timeDiff / 3600000);
		
		//find the time in minutes
		long estimateM = (timeDiff % 3600000) / 60000;

		//if there is a difference in minutes (ie, the system finds different times), check if there's a difference in hours
		if (estimateM > 0) {
			
			// set the variable to be returned  
			estimateVal = "Estimated Time until next bus: " + estimateM + " minutes";
			
			// set the label that we're going to stick the returned string on to visible
			lbl_estimate.setVisible(true);
			
			// if there's a difference in hours, change the return value
			if (estimateH != 0) {
				estimateVal = "Estimated Time until next bus: " + estimateH + " hours and " + estimateM + " minutes";
			}
		} else {
			//if there's no difference in minutes, don't show any information
			lbl_estimate.setVisible(false);
		}

		return estimateVal;
	}
	
	public void loadIcons(JLabel[] labels){
		
		for(JLabel l: labels){
			l.setIcon(tick);
			l.setVisible(false);
		}
	}
	
	public ImageIcon scaleIcon(ImageIcon img){
		Image image = img.getImage();
		Image scaled = image.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		img = new ImageIcon(scaled);  // transform it back
		return img;
	}
}
