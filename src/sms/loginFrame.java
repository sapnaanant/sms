package sms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Canvas;

public class loginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	
	public static dbCon db = null;
	private JPasswordField pswdField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginFrame frame = new loginFrame();
					frame.setTitle("SMS : Login");
					ImageIcon img = new ImageIcon("sms.jpg");
					frame.setIconImage(img.getImage());
					db = new dbCon();
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
	public loginFrame() {
		setName("loginFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("User name :");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(141, 56, 91, 27);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password   :");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(141, 94, 79, 27);
		contentPane.add(lblPassword);
		
		nameField = new JTextField();
		nameField.setBounds(254, 60, 139, 20);
 		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (db.verifyCredentials(nameField.getText(), pswdField.getText())){
					MainFrame mf = new MainFrame();
					mf.setVisible(true);
					dispose();
				}else {
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Wrong Credentials. Login Unsuccessful.");
				}
			}
		});
		btnLogin.setBounds(194, 154, 89, 23);
		contentPane.add(btnLogin);
		
		pswdField = new JPasswordField();
		pswdField.setBounds(254, 98, 139, 20);
		contentPane.add(pswdField);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUpFrame sf = new signUpFrame();
				setVisible(false);
				sf.setVisible(true);
			}
		});
		btnSignUp.setBounds(304, 154, 89, 23);
		contentPane.add(btnSignUp);
		
	}
}
