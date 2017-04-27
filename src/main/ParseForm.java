package main;

import java.util.ArrayList;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
*
* @author FlashCloud
* Form to take input of json file and convert them into sql queries
*/
@SuppressWarnings("serial")
public class ParseForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtFileName;
	private ArrayList<String> insQueries = new ArrayList<String>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ParseForm frame = new ParseForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ParseForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 465, 159);
		contentPane.add(panel);
		panel.setLayout(null);

		txtFileName = new JTextField();
		txtFileName.setBounds(215, 71, 101, 20);
		panel.add(txtFileName);
		txtFileName.setColumns(10);
		txtFileName.setText("update.json");

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("parse invoked");
				String filename = txtFileName.getText();
				System.out.println(filename + " being used");
				Parser parser = new Parser();
				insQueries = parser.parseFile(filename);
				updateDB(insQueries);

			}
		});
		btnUpdate.setBounds(326, 70, 89, 23);
		panel.add(btnUpdate);

		JLabel lblEnterTheName = new JLabel("Enter the name of the file to update");
		lblEnterTheName.setBounds(36, 74, 228, 14);
		panel.add(lblEnterTheName);
	}

	/**
	 * Updates the database with new queries
	 * @param insQueries array of insert queries used to iterate over
	 */
	private void updateDB(ArrayList<String> insQueries) {
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.connect();
		System.out.println("Attempting to insert new queries...");
		for (String qry : insQueries) {
			dbConn.runInsert(qry);
		}
		System.out.println("If there have been no errors, then the database has been successfully updated!");
	}
}
