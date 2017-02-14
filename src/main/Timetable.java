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
	static String query = "select Route_Name, Arrival_Time from Arrival_Stop natural join Arrival_Times natural join Route where Stop_ID = " + thisStop;
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
		System.out.println(query);
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		ResultSet results = dbConn.runQuery(query);
		//System.out.println(results);
		String newTextField = "";
		try {
			while (results.next()) {
				//results.next();
				Time stopTime = results.getTime("Arrival_Time");
				String name = results.getString("Route_Name");
				System.out.println(name);
				System.out.println(stopTime.toString());
				newTextField += name + "\t" + stopTime.toString() + "\n";
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updPane.setText(newTextField);
	}
}
