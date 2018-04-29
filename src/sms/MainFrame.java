package sms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
	private JTextField limitField;
	static ArrayList<String> items;

	/**
	 * Create the frame
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
		
		table = new JTable(){
			  public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col) {
				  Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
				  if (Index_col == 1){
					  if (Integer.parseInt(model.getValueAt(Index_row, 1).toString()) < Integer.parseInt(model.getValueAt(Index_row, 2).toString())){
						  comp.setBackground(Color.red);
					  }else{
						  comp.setBackground(Color.white);
					  }
				  }
				  else{
					  comp.setBackground(Color.white);
				  }
			  /*if (Index_row % 2 == 0 && !isCellSelected(Index_row, Index_col)) {
			  comp.setBackground(Color.lightGray);
			  } 
			  else {
			  comp.setBackground(Color.white);
			  }*/
			  return comp;
			  }
		};
		JScrollPane scrollPane = new JScrollPane(table);
		LineNumberTableRowHeader listTableLineNumber = new LineNumberTableRowHeader(scrollPane, table);
		listTableLineNumber.setBackground(Color.LIGHT_GRAY);
		scrollPane.setRowHeaderView(listTableLineNumber);
		String[] col = {"Name", "Quantity", "limit ()"};
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
				}else if (limitField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "limit can not be empty.");
					flag = false;
				}else if (!limitField.getText().trim().matches("[0-9]*")) {
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "limit should be number only.");
					flag = false;
				}
				if (flag){
					if (loginFrame.db.checkItemName(nameField.getText().toString())) {
						JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Item already present. Can't add the same items.");
					}else {
						loginFrame.db.addItem(nameField.getText(),Integer.parseInt(quanField.getText()),Integer.parseInt(limitField.getText()));
//						/loginFrame.db.addItem(nameField.getText(),quanField.getText(),limitField.getText());
						model.addRow(new String[] {nameField.getText(), quanField.getText(), limitField.getText()});
						items.clear();
						items = loginFrame.db.getAllItemDetails();
					}
				}
			}
		});
		btnAddItem.setBounds(451, 57, 104, 23);
		contentPane.add(btnAddItem);
		
		JButton btnPurchase = new JButton("Purchase");
		btnPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spDialog s = new spDialog(1);
				disable();
				s.setVisible(true);
			}
		});
		btnPurchase.setBounds(451, 156, 104, 23);
		contentPane.add(btnPurchase);
		
		JButton btnSell = new JButton("Sell");
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spDialog s = new spDialog(0);
				disable();
				s.setVisible(true);
			}
		});
		btnSell.setBounds(451, 122, 104, 23);
		contentPane.add(btnSell);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(35, 11, 46, 14);
		contentPane.add(lblName);
		
		nameField = new JTextField();
		nameField.setBounds(91, 8, 86, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(187, 11, 72, 14);
		contentPane.add(lblQuantity);
		
		quanField = new JTextField();
		quanField.setBounds(258, 8, 86, 20);
		contentPane.add(quanField);
		quanField.setColumns(10);
		
		JLabel lblLimit = new JLabel("Limit");
		lblLimit.setBounds(369, 11, 72, 14);
		contentPane.add(lblLimit);
		
		limitField = new JTextField();
		limitField.setBounds(445, 8, 86, 20);
		contentPane.add(limitField);
		limitField.setColumns(10);
		
		JButton btnDeleteItem = new JButton("Delete Item");
		btnDeleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()!=-1){
					int id = Integer.parseInt(items.get(table.getSelectedRow()*4));
					loginFrame.db.deleteItem(id);
					model.removeRow(table.getSelectedRow());
					items.clear();
					items = loginFrame.db.getAllItemDetails();
				}else{
					JOptionPane.showMessageDialog(contentPane.getTopLevelAncestor(), "Please select a row.");
				}
			}
		});
		btnDeleteItem.setBounds(451, 91, 104, 23);
		contentPane.add(btnDeleteItem);
		
		loadTable();
	}
	
	public void loadTable() {
		items = loginFrame.db.getAllItemDetails();
		int row = 1;
		for (int i=0; i<items.size(); i=i+4) {
			model.addRow(new String[] {
					items.get(i+1),
					items.get(i+2),
					items.get(i+3)
			});
		}
	}
}
