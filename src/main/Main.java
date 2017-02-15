package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

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
import javax.swing.border.EmptyBorder;
import javax.swing.JList;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtTime;
	private JTable table;

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
	public Main() {
		
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
		
		JLabel lblSearchForA = new JLabel("Search for a specific route");
		lblSearchForA.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblSearchForA.setBounds(257, 6, 248, 25);
		panel.add(lblSearchForA);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Locks Way Road", "Lidl", "Fratton Station", "Cambridge Road", "Winston Churchill Ave"}));
		
		comboBox.setBounds(264, 71, 241, 27);
		panel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Locks Way Road", "Lidl", "Fratton Station", "Cambridge Road", "Winston Churchill Ave", "gowno"}));
		comboBox_1.setBounds(264, 138, 241, 27);
		panel.add(comboBox_1);
		
		JLabel lblTo = new JLabel("Depart from");
		lblTo.setBounds(267, 43, 76, 16);
		panel.add(lblTo);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Arrive", "Depart"}));
		comboBox_2.setBounds(186, 177, 99, 27);
		panel.add(comboBox_2);
		
		JLabel lblMostPopularRoutes = new JLabel("Most popular routes");
		lblMostPopularRoutes.setBounds(317, 338, 133, 16);
		panel.add(lblMostPopularRoutes);
		
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(69, 246, 630, 80);
		panel.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(186, 366, 394, 80);
		panel.add(textArea_1);
		
		txtTime = new JTextField();
		txtTime.setText("Time");
		txtTime.setBounds(337, 177, 68, 26);
		panel.add(txtTime);
		txtTime.setColumns(10);
		
		JLabel lblArriveAt = new JLabel("Arrive at\n");
		lblArriveAt.setBounds(274, 110, 57, 16);
		panel.add(lblArriveAt);
		
		
				
		
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conn.connect();
				 String from = comboBox.getSelectedItem().toString();
		         String to = comboBox_1.getSelectedItem().toString();
		         String hour = txtTime.getText();
		         String route = "";
		         ArrayList<BusStop>fromStop = new ArrayList<BusStop>();
		         ArrayList<BusStop>toStop = new ArrayList<BusStop>();
		         Boolean departing;
		         if(comboBox_2.getSelectedItem().toString() == "Depart"){
		        	 departing = true;
		         }else{
		        	 departing = false;
		         }
		         
		         
		         ResultSet rs1 = conn.getSpecificRoute(from,hour,departing);
		         
		         
		         String time = "";
		         
		         
		        // System.out.println(rs);
			        try {
			        	textArea.setText("");
			        	
			        	while(rs1.next()){
			        		time = rs1.getTime("Arrival_Time").toString();
				            String stopName = rs1.getString("Stop_Name");
				            
				            fromStop.add(new BusStop(stopName,time));
				            
			        	}
			        	
			        	ResultSet rs2 = conn.getSpecificRoute(to, fromStop.get(0).getTime(), departing);
			        	
			        	while(rs2.next()){
			            	
			            	time = rs2.getTime("Arrival_Time").toString();
				            String stopName = rs2.getString("Stop_Name");
				            
				            toStop.add(new BusStop(stopName,time));
			        	}
				           
			        
				            
			        	 for(int i = 0; i < fromStop.size(); i++){
			        		 	//System.out.println(fromStop.get(i).getTime() + fromStop.get(i).getBusName());
			        		 	//System.out.println(toStop.get(i).getTime() + toStop.get(i).getBusName());
			        		 	
			        		 
			        		 	
					        	route =  "Depart at " + fromStop.get(i).getTime() + " Arrive at " + toStop.get(i).getTime() + 
					        	" From " + fromStop.get(i).getBusName() + " To " + toStop.get(i).getBusName();
					        	textArea.append(route + "\n");
					        }
			        	
				            } catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        
			}
		});
		btnNewButton.setBounds(453, 176, 117, 29);
		panel.add(btnNewButton);
		
		
		JToggleButton btn_font = new JToggleButton("Font");
		btn_font.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        JComponent elementList[] = {lblSearchForA, lblTo, comboBox, lblArriveAt, comboBox_1, comboBox_2, txtTime, btnNewButton, lblMostPopularRoutes};
		        
		        if (btn_font.isSelected()) {
		            for (JComponent element : elementList) {
		               element.setFont(new Font("Arial", Font.PLAIN, element.getFont().getSize() +  5 ));
		            }
		        }
		        
		        
		        else {
		            for (JComponent element : elementList) {
		               element.setFont(new Font("Arial", Font.PLAIN, element.getFont().getSize() -  5 ));
		            }
		        }
			}
		});
		btn_font.setBounds(10, 415, 99, 23);
		panel.add(btn_font);
		
		
		
		
		
		
		
		
	}
}
