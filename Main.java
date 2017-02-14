import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Locks Way Road", "LIDl", "Fratton Station", "CambrIDge Road", "Winston Churchill Ave"}));
		
		comboBox.setBounds(264, 71, 241, 27);
		panel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Locks Way Road", "LIDl", "Fratton Station", "CambrIDge Road", "Winston Churchill Ave"}));
		comboBox_1.setBounds(264, 138, 241, 27);
		panel.add(comboBox_1);
		
		JLabel lblTo = new JLabel("Depart from");
		lblTo.setBounds(267, 43, 76, 16);
		panel.add(lblTo);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Arrive", "Depart"}));
		comboBox_2.setBounds(274, 177, 99, 27);
		panel.add(comboBox_2);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(123, 234, 499, 66);
		panel.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(123, 366, 499, 66);
		panel.add(textPane_1);
		
		JLabel lblMostPopularRoutes = new JLabel("Most popular routes");
		lblMostPopularRoutes.setBounds(317, 338, 133, 16);
		panel.add(lblMostPopularRoutes);
		
		txtTime = new JTextField();
		txtTime.setText("Time");
		txtTime.setBounds(385, 177, 43, 26);
		panel.add(txtTime);
		txtTime.setColumns(10);
		
		JLabel lblArriveAt = new JLabel("Arrive at\n");
		lblArriveAt.setBounds(274, 110, 57, 16);
		panel.add(lblArriveAt);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conn.connect();
				 String from = comboBox_1.getSelectedItem().toString();
		         String to = comboBox_2.getSelectedItem().toString();
		         ResultSet rs = conn.getSpecificRoute("tblStop", from, to);
		         
		        // System.out.println(rs);
			        try {
			        	while(rs.next()){
			        		
							rs.next();
							int id = rs.getInt("Stop_ID");
							int aid = rs.getInt("Arrival_ID");
					     Time time = rs.getTime("Arrival_Time");
					     String StopName = rs.getString("Stop_Name");
					     String route = time.toString() + ' ' + StopName;
					     //textPane.setText(StopName);
					     System.out.println(route);
			        	}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        
			}
		});
		btnNewButton.setBounds(440, 177, 117, 29);
		panel.add(btnNewButton);
	}
}
