package main;

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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtTime;
	private JLabel lbl_estimate;
	private JToggleButton btn_font;
	private JTextArea txtHints;
	private JTable table_2;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
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

		DatabaseConnection conn = new DatabaseConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 821, 772);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btn_font = new JToggleButton("Toggle Font Size");
		btn_font.setToolTipText("Tap to increase / decrease font size");

		btn_font.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtHints.setText("Tap to increase / decrease font size");

			}
		});
		btn_font.setBounds(620, 11, 155, 23);

		JPanel panel = new JPanel();
		panel.setBounds(10, 6, 785, 717);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblSearchForA = new JLabel("Search for a specific route");
		lblSearchForA.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblSearchForA.setBounds(198, 6, 295, 25);
		panel.add(lblSearchForA);

		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("test");
		comboBox.setModel(new DefaultComboBoxModel(
				new String[] {
					"Locks Way Road",
					"Lidl",
					"Fratton Station",
					"Cambridge Road",
					"Winston Churchill Ave"
				})
			);

		comboBox.setBounds(264, 71, 241, 27);
		panel.add(comboBox);

		JComboBox combo_Arrive = new JComboBox();
		combo_Arrive.setModel(new DefaultComboBoxModel(
				new String[] { 
					"Locks Way Road",
					"Lidl",
					"Fratton Station",
					"Cambridge Road",
					"Winston Churchill Ave"
					})
				);
		combo_Arrive.setBounds(264, 138, 241, 27);
		panel.add(combo_Arrive);

		JLabel lblTo = new JLabel("Depart from");
		lblTo.setBounds(267, 43, 108, 16);
		panel.add(lblTo);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "Arrive", "Depart" }));
		comboBox_2.setBounds(186, 177, 110, 27);
		panel.add(comboBox_2);

		JLabel lblMostPopularRoutes = new JLabel("Most popular routes");
		lblMostPopularRoutes.setHorizontalAlignment(SwingConstants.CENTER);
		lblMostPopularRoutes.setBounds(10, 417, 765, 16);

		panel.add(lblMostPopularRoutes);

		txtHints = new JTextArea();
		txtHints.setBounds(10, 626, 765, 80);
		panel.add(txtHints);
		txtHints.setText("Welcome to FlashClould! Tap [Search] to display all buses coming here next.");

		txtTime = new JTextField();
		txtTime.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtHints.setText("Enter the time you want to arrive / depart at");
			}
		});
		java.util.Date currentTime = new java.util.Date();
		DateFormat formatter = new SimpleDateFormat("hh:mm");
		txtTime.setText(formatter.format(currentTime));
		txtTime.setBounds(315, 176, 99, 26);
		panel.add(txtTime);
		txtTime.setColumns(10);

		JLabel lblArriveAt = new JLabel("Arrive at\n");
		lblArriveAt.setBounds(264, 111, 60, 16);
		panel.add(lblArriveAt);

		JButton btn_Search = new JButton("Search");
		btn_Search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				txtHints.setText("Tap [Search] to display all buses coming here next.");
			}
		});
		btn_Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conn.connect();
				String from = comboBox.getSelectedItem().toString();
				String to = combo_Arrive.getSelectedItem().toString();
				String hour = txtTime.getText();
				@SuppressWarnings("unused")
				String route = "";
				Boolean departing = (comboBox_2.getSelectedItem().toString() == "Depart");
				search(from, to, hour, departing, table_2);

			}
		});

		btn_Search.setBounds(437, 176, 133, 29);
		panel.add(btn_Search);

		JLabel lblHints = new JLabel("Hints");
		lblHints.setBounds(10, 601, 46, 14);
		panel.add(lblHints);

		btn_font.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComponent elementList[] = { lbl_estimate, lblHints, lblSearchForA, lblTo, comboBox, lblArriveAt,
						combo_Arrive, comboBox_2, txtTime, btn_Search, lblMostPopularRoutes };
				Boolean enlargeFont = btn_font.isSelected();
				changeFontSize(enlargeFont, elementList);

			}
		});
		
		panel.add(btn_font);
		lbl_estimate = new JLabel("Estimated Time until next bus: ");
		lbl_estimate.setBounds(107, 381, 586, 25);
		panel.add(lbl_estimate);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(102, 208, 619, 162);
		panel.add(scrollPane);

		table_2 = new JTable();
		scrollPane.setViewportView(table_2);

		JButton btnViewTimetables = new JButton("View timetables");
		btnViewTimetables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Timetable timeForm = new Timetable();
				timeForm.setVisible(true);
			}
		});
		btnViewTimetables.setBounds(620, 40, 155, 23);
		panel.add(btnViewTimetables);

		JTextPane txtpnPopRoutes = new JTextPane();
		txtpnPopRoutes.setBounds(32, 445, 741, 138);
		panel.add(txtpnPopRoutes);

		getOrderedPopularRoutes(txtpnPopRoutes);
	}
	
	public void search(String from, String to, String hour, Boolean departing, JTable ourTable) {
		DatabaseConnection conn = new DatabaseConnection();
		conn.connect();
		ArrayList<BusStop> fromStop = new ArrayList<BusStop>();
		ArrayList<BusStop> toStop = new ArrayList<BusStop>();
		ResultSet rs1 = conn.getSpecificRoute(from, hour, departing);
		ResultSet popRoutes = conn.getPopRoutes(from, hour, departing);
		System.out.println("Stop IDs");
		try {
			// only runs if there's data returned, therefore no
			// validation is requried.
			while (popRoutes.next()) {
				int curID = popRoutes.getInt("Route_ID");
				int hitCount = popRoutes.getInt("Route_Count");
				int newHits = hitCount + 1;
				String query = "update Popular set Route_Count=" + newHits + " where Route_ID=" + curID;
				conn.runInsert(query);
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		System.out.println("end of stop ids");
		ResultSet rs2;
		String time;
		try {
			while (rs1.next()) {
				time = rs1.getTime("Arrival_Time").toString();
				String stopName = rs1.getString("Stop_Name");
				fromStop.add(new BusStop(stopName, time));
			}
			if (departing) {
				rs2 = conn.getSpecificRoute(to, fromStop.get(0).getTime(), departing);
				DateFormat formatter = new SimpleDateFormat("hh:mm");
				java.util.Date currentTime = new java.util.Date();
				java.util.Date arriveTime = formatter.parse(fromStop.get(0).getTime());
				System.err.println(currentTime);
				System.err.println(arriveTime);
				long timeDiff = currentTime.getTime() - arriveTime.getTime();
				String estimate = (timeDiff / 3600000) + " hours and "
				                + (timeDiff % 3600000) / 60000 + " minutes";
				lbl_estimate.setText(lbl_estimate.getText() + estimate);

			} else {
				rs2 = conn.getSpecificRoute(to, hour, departing);
			}
			while (rs2.next()) {
				time = rs2.getTime("Arrival_Time").toString();
				String stopName = rs2.getString("Stop_Name");
				toStop.add(new BusStop(stopName, time));
			}
			DefaultTableModel model = new DefaultTableModel(
					new String[] { 
							"Depart at",
							"Arrive at",
							"From",
							"To",
							"Travel time",
							"Estimated Price"
							},
					0);
			for (int i = 0; i < toStop.size(); i++) {
				String travel = fromStop.get(i).calculateTravelTime(toStop.get(i).getTime());
				String price = fromStop.get(i).calculateCost(toStop.get(i).getTime());
				if (!travel.equals("past")) {
					model.addRow(
						new Object[] {
							fromStop.get(i).getTime(),
							toStop.get(i).getTime(),
							fromStop.get(i).getBusName(),
							toStop.get(i).getBusName(),
							travel,
							price
							}
						);
					ourTable.setModel(model);
				}
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
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
		if (btn_font.isSelected()) {
		  for (JComponent element : elementList) {
			element.setFont(new Font("Arial", Font.PLAIN, element.getFont().getSize() + 5));
		  }
		} else {
		  for (JComponent element : elementList) {
			element.setFont(new Font("Arial", Font.PLAIN, element.getFont().getSize() - 5));
		  }
		}
	}
}
