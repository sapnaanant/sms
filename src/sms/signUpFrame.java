package sms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class signUpFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField pswdField;
	private JTextField rePswdField;
	/**
	 * Create the frame
	 */
	public signUpFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username : ");
		lblUsername.setBounds(83, 53, 116, 14);
		contentPane.add(lblUsername);
		
		nameField = new JTextField();
		nameField.setBounds(211, 50, 86, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(83, 78, 116, 14);
		contentPane.add(lblPassword);
		
		pswdField = new JTextField();
		pswdField.setBounds(211, 75, 86, 20);
		contentPane.add(pswdField);
		pswdField.setColumns(10);
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password :");
		lblReenterPassword.setBounds(83, 103, 116, 14);
		contentPane.add(lblReenterPassword);
		
		rePswdField = new JTextField();
		rePswdField.setBounds(211, 100, 86, 20);
		contentPane.add(rePswdField);
		rePswdField.setColumns(10);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginFrame.db.checkUsername(nameField.getText().trim())){
					loginFrame.db.addCredentials(nameField.getText().trim(), pswdField.getText().trim());
					//show dialog box saying successful or failure
				}
			}
		});
		btnSignUp.setBounds(152, 142, 89, 23);
		contentPane.add(btnSignUp);
		
		JButton btnCheckAvailablity = new JButton("Check Availablity");
		btnCheckAvailablity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginFrame.db.checkUsername(nameField.getText().trim())){
					//show option dialog saying available or not
				}
			}
		});
		btnCheckAvailablity.setBounds(306, 49, 118, 23);
		contentPane.add(btnCheckAvailablity);
	}
}