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

public class ParseForm extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtTime;
	private JTable table;
	private JTextField txtFileName;

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
				String filename = txtFileName.getText();
				Parser parser = new Parser();
				parser.parseFile(filename);
			}
		});
		btnUpdate.setBounds(326, 70, 89, 23);
		panel.add(btnUpdate);
		
		JLabel lblEnterTheName = new JLabel("Enter the name of the file to update");
		lblEnterTheName.setBounds(36, 74, 228, 14);
		panel.add(lblEnterTheName);
	}
}
