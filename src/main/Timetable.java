package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Timetable extends JFrame {
	
	static String thisStop = "101";
	static String qryGetRouteNums = "select distinct Route_ID, Route_Name from Arrival_Stop natural join Route natural join Arrival_Times where Stop_ID = " + thisStop;
	static String qryGetRoutes = "Select Arrival_time from Arrival_Stop natural join Route natural join Arrival_Times where Route_ID = ";
	private JPanel contentPane;
	
	public Timetable() {
		DatabaseConnection conn = new DatabaseConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 738, 466);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNextBus = new JLabel("Next Bus: ?? Minutes");
		lblNextBus.setHorizontalAlignment(SwingConstants.CENTER);
		lblNextBus.setBounds(12, 41, 714, 37);
		panel.add(lblNextBus);
		
		JButton btnDay = new JButton("Day");
		btnDay.setBounds(322, 149, 117, 25);
		panel.add(btnDay);
		
		JButton btnWeek = new JButton("Week");
		btnWeek.setBounds(451, 149, 117, 25);
		panel.add(btnWeek);
		
		JButton btnMonth = new JButton("Month");
		btnMonth.setBounds(582, 149, 117, 25);
		panel.add(btnMonth);
		
		JTextPane txtpnTimes = new JTextPane();
		txtpnTimes.setText("null");
		txtpnTimes.setBounds(12, 218, 687, 236);
		panel.add(txtpnTimes);
		
		JButton btnTemp = new JButton("temp");
		btnTemp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getTimes(txtpnTimes);
			}
		});
		btnTemp.setBounds(76, 131, 117, 25);
		panel.add(btnTemp);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Timetable frame = new Timetable();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	public static void getTimes(JTextPane updPane) {
		System.out.println(qryGetRouteNums);
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		ResultSet routeNumbers = dbConn.runQuery(qryGetRouteNums);
		//System.out.println(results);
		String newTextField = "";
		try {
			
			while (routeNumbers.next()) {
				newTextField += routeNumbers.getString("Route_Name") + ":\t\t";
				String qryGetTimes = qryGetRoutes +  routeNumbers.getInt("Route_ID") + " order by Arrival_Time";
				ResultSet times = dbConn.runQuery(qryGetTimes);
				while (times.next()){
					newTextField += times.getTime("Arrival_Time").toString() + "\t";
				}
				newTextField += "\n";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updPane.setText(newTextField);
	}
}
