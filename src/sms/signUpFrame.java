package sms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class signUpFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JPasswordField pswdField;
	private JPasswordField rePswdField;
	/**
	 * Create the frame
	 */
	public signUpFrame() {
		setName("SignUpFrame");
		setTitle("SMS : SignUP");
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
		nameField.setBounds(211, 50, 86, 17);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(83, 78, 116, 14);
		contentPane.add(lblPassword);
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password :");
		lblReenterPassword.setBounds(83, 103, 116, 14);
		contentPane.add(lblReenterPassword);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginFrame.db.checkUsername(nameField.getText().trim())){
					if (!pswdField.getText().trim().equals("") && !rePswdField.getText().trim().equals("")){
						if (pswdField.getText().trim().equals(rePswdField.getText().trim())){
							loginFrame.db.addCredentials(nameField.getText().trim(), pswdField.getText().trim());
							JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "New User Added. Login Now.");
							JFrame.getFrames()[0].setVisible(true);
							dispose();
						}else{
							JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Re-entered password does not match");
						}
					}else{
						JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Some fields are empty.");
					}
				}else{
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Username not available.");
				}
			}
		});
		btnSignUp.setBounds(152, 142, 89, 23);
		contentPane.add(btnSignUp);
		
		JButton btnCheckAvailablity = new JButton("Check Availablity");
		btnCheckAvailablity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginFrame.db.checkUsername(nameField.getText().trim())){
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Username is available.");
				}else{
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Username not available.");
				}
			}
		});
		btnCheckAvailablity.setBounds(306, 49, 118, 23);
		contentPane.add(btnCheckAvailablity);
		
		pswdField = new JPasswordField();
		pswdField.setBounds(211, 75, 86, 17);
		contentPane.add(pswdField);
		
		rePswdField = new JPasswordField();
		rePswdField.setBounds(211, 100, 86, 17);
		contentPane.add(rePswdField);
	}
}
