package sms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import sun.applet.Main;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class spDialog extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private JTextField quanField;
	private JTextField dateField;
	private DefaultTableModel model;
	/**
	 * Create the dialog.
	 */
	public spDialog(int flag) { //flag = 0(sell), 1(purchase)
		setName("SellPurchaseFrame");
		if (flag == 0){
			setTitle("SMS : Sell");
		}else{
			setTitle("SMS : Purchase");
		}
		setBounds(100, 100, 451, 476);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		ArrayList<String> allItems = new ArrayList<>();
		for (int i=0; i<MainFrame.items.size(); i+=4)
			allItems.add(MainFrame.items.get(i+1));
		JComboBox itemsComboBox = new JComboBox();
		itemsComboBox.setBounds(209, 59, 104, 20);
		itemsComboBox.setModel(new DefaultComboBoxModel<>(allItems.toArray()));
		contentPanel.add(itemsComboBox);
		
		JLabel lblItems = new JLabel("Items");
		lblItems.setBounds(90, 62, 46, 14);
		contentPanel.add(lblItems);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(90, 102, 75, 14);
		contentPanel.add(lblQuantity);
		
		quanField = new JTextField();
		quanField.setBounds(209, 99, 104, 20);
		contentPanel.add(quanField);
		quanField.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(90, 141, 46, 14);
		contentPanel.add(lblDate);
		
		dateField = new JTextField();
		dateField.setEnabled(false);
		dateField.setColumns(10);
		dateField.setBounds(209, 138, 104, 20);
		contentPanel.add(dateField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 166, 434, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!quanField.getText().trim().equals("")){
							if (quanField.getText().trim().matches("[0-9]*")){
								if (!dateField.getText().trim().equals("")){
									//directly update the database
									int value = -1;
									int index = -1;
									int modelI = 0;
									for (int i=0; i<MainFrame.items.size(); i=i+4) {
										if (MainFrame.items.get(i+1).equals(itemsComboBox.getSelectedItem())) {
											value = Integer.parseInt(MainFrame.items.get(i+2));
											index = Integer.parseInt(MainFrame.items.get(i));
											break;
										}
										modelI++;
									}
									if (flag==0) {
										if(value-Integer.parseInt(quanField.getText()) >= 0) {
											loginFrame.db.updateItem(index,value-Integer.parseInt(quanField.getText()));
											loginFrame.db.addToSellT(dateField.getText(), itemsComboBox.getSelectedItem().toString(), Integer.parseInt(quanField.getText()));
											MainFrame.model.setValueAt(value-Integer.parseInt(quanField.getText()),modelI, 1);
											MainFrame.items.set(4*modelI+2,(value-Integer.parseInt(quanField.getText()))+"");
										}else{
											JOptionPane.showMessageDialog(contentPanel.getTopLevelAncestor(), "Not enough in the stock.");
										}
									}else {
										loginFrame.db.updateItem(index,value+Integer.parseInt(quanField.getText()));
										loginFrame.db.addToPurT(dateField.getText(), itemsComboBox.getSelectedItem().toString(), Integer.parseInt(quanField.getText()));
										MainFrame.model.setValueAt(value+Integer.parseInt(quanField.getText()),modelI, 1);
										MainFrame.items.set(4*modelI+2,(value+Integer.parseInt(quanField.getText()))+"");
										MainFrame.table.repaint();
									}
									JFrame.getFrames()[1].setEnabled(true);
									dispose();
								}else{
									JOptionPane.showMessageDialog(contentPanel.getTopLevelAncestor(), "Please Choose a date by clicking on button besides date text field");
								}
							}else{
								JOptionPane.showMessageDialog(contentPanel.getTopLevelAncestor(), "Quantity Field should contain number.");
							}
						}else{
							JOptionPane.showMessageDialog(contentPanel.getTopLevelAncestor(), "Please Choose a date by clicking on button besides date text field");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFrame.getFrames()[1].setEnabled(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		JTable table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		String[] col = {"Sl. No." , "Date", "Name", "Quantity"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(col);
		table.setModel(model);
		scrollPane.setBounds(0, 210, 434, 216);
		contentPanel.add(scrollPane);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateField.setText(new DatePicker().setPickedDate());
			}
		});
		button.setBounds(323, 137, 22, 23);
		contentPanel.add(button);
		loadTable(flag);
	}
	
	public void loadTable(int flag) {
		ArrayList<String> data;
		if (flag == 0){
			data = loginFrame.db.getAllSellData();
		}else{
			data = loginFrame.db.getAllPurData();
		}
		
		int row = 1;
		for (int i=0; i<data.size(); i=i+4) {
			model.addRow(new String[] {
					(row++)+"",
					data.get(i+1),
					data.get(i+2),
					data.get(i+3)
			});
		}
	}
}
