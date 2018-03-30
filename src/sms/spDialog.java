package sms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class spDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField quanField;
	private JTextField dateField;

	/**
	 * Create the dialog.
	 */
	public spDialog(int flag) { //flag = 0(sell), 1(purchase)
		setBounds(100, 100, 450, 300);
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
		dateField.setColumns(10);
		dateField.setBounds(209, 138, 104, 20);
		contentPanel.add(dateField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//add check for availability
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
							if(value-Integer.parseInt(quanField.getText()) > 0) {
								loginFrame.db.updateItem(index,value-Integer.parseInt(quanField.getText()));
								MainFrame.model.setValueAt(value-Integer.parseInt(quanField.getText()),modelI, 2);
								MainFrame.items.set(4*modelI+2,(value-Integer.parseInt(quanField.getText()))+"");
							}
						}else {
							loginFrame.db.updateItem(index,value+Integer.parseInt(quanField.getText()));
							MainFrame.model.setValueAt(value+Integer.parseInt(quanField.getText()),modelI, 2);
							MainFrame.items.set(4*modelI+2,(value+Integer.parseInt(quanField.getText()))+"");
						}
						dispose();
						JFrame.getFrames()[0].enable();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
