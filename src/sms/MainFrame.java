package sms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.lang.model.element.QualifiedNameable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	static JTable table;
	static DefaultTableModel model;
	private JTextField nameField;
	private JTextField quanField;
	private JTextField priceField;
	static ArrayList<String> items;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setName("MainFrame");
		setTitle("SMS : Items");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 581, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		String[] col = {"Sl. No." , "Name", "Quantity", "Price (Rs.)"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(col);
		table.setModel(model);
		scrollPane.setBounds(35, 61, 406, 367);
		contentPane.add(scrollPane);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean flag = true;
				if (nameField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Name can not be empty.");
					flag = false;
				}else if (quanField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Quantity can not be empty.");
					flag = false;
				}else if (!quanField.getText().trim().matches("[0-9]*")) {
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Quantity should be number only.");
					flag = false;
				}else if (priceField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Price can not be empty.");
					flag = false;
				}else if (!priceField.getText().trim().matches("[0-9]*")) {
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Price should be number only.");
					flag = false;
				}
				if (flag){
					if (loginFrame.db.checkItemName(nameField.getText().toString())) {
						JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Item already present. Can't add the same items.");
					}else {
						loginFrame.db.addItem(nameField.getText(),quanField.getText(),priceField.getText());
						model.addRow(new String[] {(model.getRowCount()+1)+"", nameField.getText(), quanField.getText(), priceField.getText()});
					}
				}
			}
		});
		btnAddItem.setBounds(466, 57, 89, 23);
		contentPane.add(btnAddItem);
		
		JButton btnPurchase = new JButton("Purchase");
		btnPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spDialog s = new spDialog(1);
				disable();
				s.setVisible(true);
			}
		});
		btnPurchase.setBounds(466, 125, 89, 23);
		contentPane.add(btnPurchase);
		
		JButton btnSell = new JButton("Sell");
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spDialog s = new spDialog(0);
				disable();
				s.setVisible(true);
			}
		});
		btnSell.setBounds(466, 91, 89, 23);
		contentPane.add(btnSell);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(35, 11, 46, 14);
		contentPane.add(lblName);
		
		nameField = new JTextField();
		nameField.setBounds(91, 8, 86, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(198, 11, 46, 14);
		contentPane.add(lblQuantity);
		
		quanField = new JTextField();
		quanField.setBounds(258, 8, 86, 20);
		contentPane.add(quanField);
		quanField.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(375, 11, 46, 14);
		contentPane.add(lblPrice);
		
		priceField = new JTextField();
		priceField.setBounds(445, 8, 86, 20);
		contentPane.add(priceField);
		priceField.setColumns(10);
		
		loadTable();
	}
	
	public void loadTable() {
		items = loginFrame.db.getAllItemDetails();
		int row = 1;
		for (int i=0; i<items.size(); i=i+4) {
			model.addRow(new String[] {
					(row++)+"",
					items.get(i+1),
					items.get(i+2),
					items.get(i+3)
					
			});
		}
	}
}
