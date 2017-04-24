package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtHour;
	private JLabel lbl_estimate;
	private JToggleButton btn_font;
	private JTextArea txtHints;
	private JTable table_2;
	private JTextField txtMinute;
	private JPanel panel_1;
	private JLabel lblSorryNoRoutes;
	private String currentTime;
	private String estimateVal;
	private SimpleDateFormat format;

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
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Main() {
		currentTime = "";
		estimateVal = "";
		format = new SimpleDateFormat("HH:mm:ss");
		DatabaseConnection conn = new DatabaseConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 821, 772);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btn_font = new JToggleButton("Increase Font Size");
		btn_font.setForeground(new Color(0, 0, 128));
		btn_font.setBackground(new Color(100, 149, 237));
		btn_font.setToolTipText("Tap to increase / decrease font size");

		btn_font.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtHints.setText("Tap to increase / decrease font size");

			}
		});
		btn_font.setBounds(620, 11, 155, 23);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(10, 6, 785, 717);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblSearchForA = new JLabel("Search for a specific route");
		lblSearchForA.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchForA.setForeground(new Color(0, 0, 128));
		lblSearchForA.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblSearchForA.setBounds(10, -11, 765, 55);
		panel.add(lblSearchForA);

		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		comboBox.setForeground(new Color(0, 0, 128));
		comboBox.setToolTipText("");
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Locks Way Road", "Lidl", "Fratton Station",
				"Cambridge Road", "Winston Churchill Ave" }));

		comboBox.setBounds(251, 71, 282, 47);

		panel.add(comboBox);

		JComboBox combo_Arrive = new JComboBox();
		combo_Arrive.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		combo_Arrive.setForeground(new Color(0, 0, 128));
		combo_Arrive.setModel(new DefaultComboBoxModel(new String[] { "Locks Way Road", "Lidl", "Fratton Station",
				"Cambridge Road", "Winston Churchill Ave" }));
		combo_Arrive.setSelectedIndex(2);
		combo_Arrive.setBounds(251, 130, 282, 37);
		panel.add(combo_Arrive);

		JLabel lblTo = new JLabel("Depart from");
		lblTo.setForeground(new Color(0, 0, 128));
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTo.setBounds(258, 60, 108, 16);
		panel.add(lblTo);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		comboBox_2.setForeground(new Color(0, 0, 128));
		comboBox_2.setBackground(new Color(255, 255, 255));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "Arrive", "Depart" }));
		comboBox_2.setSelectedIndex(1);
		comboBox_2.setBounds(274, 168, 233, 25);
		panel.add(comboBox_2);

		JLabel lblMostPopularRoutes = new JLabel("Most popular routes");
		lblMostPopularRoutes.setForeground(new Color(0, 0, 128));
		lblMostPopularRoutes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMostPopularRoutes.setHorizontalAlignment(SwingConstants.CENTER);
		lblMostPopularRoutes.setBounds(10, 469, 765, 16);

		panel.add(lblMostPopularRoutes);

		txtHints = new JTextArea();
		txtHints.setLineWrap(true);
		txtHints.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		txtHints.setBounds(10, 626, 765, 80);
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
		txtHour.setBounds(278, 200, 71, 53);
		panel.add(txtHour);
		txtHour.setColumns(2);

		JLabel lblArriveAt = new JLabel("Arrive at\n");
		lblArriveAt.setForeground(new Color(0, 0, 128));
		lblArriveAt.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblArriveAt.setBounds(261, 117, 88, 16);
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
				Boolean departing = (comboBox_2.getSelectedItem().toString() == "Depart");
				conn.connect();
				String from = comboBox.getSelectedItem().toString();
				String to = combo_Arrive.getSelectedItem().toString();
				if (departing) {
					fixedHour = Integer.parseInt(txtHour.getText()) - 1;
					fixedMin = Integer.parseInt(txtMinute.getText());
				} else {
					fixedHour = Integer.parseInt(txtHour.getText());
					fixedMin = Integer.parseInt(txtMinute.getText()) + 15;
				}
				String hour = fixedHour + ":" + fixedMin;
				@SuppressWarnings("unused")
				String route = "";

				search(from, to, hour, departing, table_2);

			}
		});

		btn_Search.setBounds(300, 265, 164, 37);
		panel.add(btn_Search);

		JLabel lblHints = new JLabel("Hints");
		lblHints.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHints.setBounds(10, 610, 46, 14);
		panel.add(lblHints);

		btn_font.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComponent elementList[] = { lbl_estimate, lblHints, lblSearchForA, lblTo, comboBox, lblArriveAt,
						combo_Arrive, comboBox_2, txtHour, btn_Search, lblMostPopularRoutes };
				Boolean enlargeFont = btn_font.isSelected();
				changeFontSize(enlargeFont, elementList);

			}
		});

		panel.add(btn_font);
		lbl_estimate = new JLabel("Estimated Time until next bus: ");
		lbl_estimate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_estimate.setVisible(false);
		lbl_estimate.setForeground(new Color(0, 0, 128));
		lbl_estimate.setBounds(10, 432, 745, 25);
		panel.add(lbl_estimate);

		panel_1 = new JPanel();
		panel_1.setBounds(10, 314, 765, 113);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 765, 113);
		panel_1.add(scrollPane);

		table_2 = new JTable();
		table_2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		scrollPane.setViewportView(table_2);

		JLabel lblNewLabel = new JLabel("New label");
		scrollPane.setColumnHeaderView(lblNewLabel);

		JButton btnViewTimetables = new JButton("View timetables");
		btnViewTimetables.setForeground(new Color(0, 0, 128));
		btnViewTimetables.setBackground(new Color(100, 149, 237));
		btnViewTimetables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Timetable timeForm = new Timetable();
				timeForm.setVisible(true);
			}
		});
		btnViewTimetables.setBounds(620, 40, 155, 23);
		panel.add(btnViewTimetables);

		JTextPane txtpnPopRoutes = new JTextPane();
		txtpnPopRoutes.setBounds(10, 486, 765, 103);
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
		txtMinute.setBounds(423, 200, 71, 53);
		panel.add(txtMinute);

		currentTime += txtHour.getText() + ":" + txtMinute.getText() + ":" + "00";

		JLabel label = new JLabel(":");
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label.setBounds(382, 209, 12, 37);
		panel.add(label);

		lblSorryNoRoutes = new JLabel("Sorry, no routes have been found");
		lblSorryNoRoutes.setHorizontalAlignment(SwingConstants.CENTER);
		lblSorryNoRoutes.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblSorryNoRoutes.setBounds(10, 349, 765, 37);
		panel.add(lblSorryNoRoutes);

		comboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				disableSearch(comboBox, combo_Arrive, btn_Search);
			}

		});

		combo_Arrive.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				disableSearch(comboBox, combo_Arrive, btn_Search);
			}

		});

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
				panel_1.setVisible(false);
				lblSorryNoRoutes.setVisible(true);
			} else {
				panel_1.setVisible(true);
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
									   .calculateCost(toStop.get(i)
											                .getTime()
											                );
				
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
		
		//close the database connection!
		conn.closeConnection();
	}

	public void getOrderedPopularRoutes(JTextPane popPane) {
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		String query = "SELECT Route_Name FROM Popular NATURAL JOIN Route ORDER BY Route_Count";
		ResultSet popRoutes = dbConn.runQuery(query);
		String orderRoutes = "";
		try {
			while (popRoutes.next()) {
				String routeName = popRoutes.getString("Route_Name");
				orderRoutes += routeName + "\n";
			}
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
		popPane.setText(orderRoutes);
	}

	public void changeFontSize(Boolean enlarge, JComponent[] elementList) {
		if (enlarge) {
			for (JComponent element : elementList) {
				element.setFont(new Font("Arial", Font.PLAIN, element.getFont().getSize() + 5));
				btn_font.setText("Decrease Font Size");
			}
		} else {
			for (JComponent element : elementList) {
				element.setFont(new Font("Arial", Font.PLAIN, element.getFont().getSize() - 5));
				btn_font.setText("Increase Font Size");
			}
		}
	}

	public void disableSearch(JComboBox<String> comboBox, JComboBox<String> combo_Arrive, JButton btn_Search) {
		String from = comboBox.getSelectedItem().toString();
		String to = combo_Arrive.getSelectedItem().toString();
		if (from.equals(to)) {
			btn_Search.setEnabled(false);
		} else {
			btn_Search.setEnabled(true);
		}
	}

	public void validInput(JTextField txt, JButton btn_Search) {
		String text = txt.getText();
		if (text.isEmpty()) {
			btn_Search.setEnabled(false);
		} else {

			try {
				int val = Integer.parseInt(text);
				if (txt.getName().equals("hour")) {
					if (val > 24) {
						btn_Search.setEnabled(false);
					} else {
						btn_Search.setEnabled(true);
					}
				} else if (txt.getName().equals("minute")) {
					if (val > 59) {
						btn_Search.setEnabled(false);
					} else {
						btn_Search.setEnabled(true);
					}
				}

			} catch (NumberFormatException e) {
				btn_Search.setEnabled(false);
			}
		}
	}

	public String timeDifference(Date time1, Date time2) throws ParseException {

		long timeDiff = time2.getTime() - time1.getTime();
		long estimateH = (timeDiff / 3600000);
		long estimateM = (timeDiff % 3600000) / 60000;
		System.out.println(estimateH);
		System.out.println(estimateM);

		if (estimateM > 0) {
			if (estimateH == 0) {
				estimateVal = ("Estimated Time until next bus: " + estimateM + " minutes");
			} else {
				estimateVal = ("Estimated Time until next bus: " + estimateH + " hours and " + estimateM + " minutes");
			}
		}

		if (estimateVal.length() > 0) {
			lbl_estimate.setVisible(true);
		}

		return estimateVal;
	}
}
