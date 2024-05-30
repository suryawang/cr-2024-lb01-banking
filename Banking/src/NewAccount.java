import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.CustomerRecord;
import repository.CustomerRepository;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class NewAccount extends JInternalFrame implements ActionListener {

	private JPanel jpInfo = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbDeposit;
	private JTextField txtNo, txtName, txtDeposit;
	private JComboBox cboMonth, cboDay, cboYear;
	private JButton btnSave, btnCancel;

	NewAccount() {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("Create New Account", false, true, false, true);
		setSize(335, 235);

		jpInfo.setBounds(0, 0, 500, 115);
		jpInfo.setLayout(null);

		lbNo = new JLabel("Account No:");
		lbNo.setForeground(Color.black);
		lbNo.setBounds(15, 20, 80, 25);
		lbName = new JLabel("Person Name:");
		lbName.setForeground(Color.black);
		lbName.setBounds(15, 55, 80, 25);
		lbDate = new JLabel("Deposit Date:");
		lbDate.setForeground(Color.black);
		lbDate.setBounds(15, 90, 80, 25);
		lbDeposit = new JLabel("Dep. Amount:");
		lbDeposit.setForeground(Color.black);
		lbDeposit.setBounds(15, 125, 80, 25);

		txtNo = new JTextField();
		txtNo.setHorizontalAlignment(JTextField.RIGHT);
		txtNo.setBounds(105, 20, 205, 25);
		txtName = new JTextField();
		txtName.setBounds(105, 55, 205, 25);
		txtDeposit = new JTextField();
		txtDeposit.setHorizontalAlignment(JTextField.RIGHT);
		txtDeposit.setBounds(105, 125, 205, 25);

		// Restricting The User Input to only Numerics in Numeric TextBoxes.
		txtNo.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				char c = ke.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					getToolkit().beep();
					ke.consume();
				}
			}
		});
		txtDeposit.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				char c = ke.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					getToolkit().beep();
					ke.consume();
				}
			}
		});

		// Creating Date Option.
		String Months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		cboMonth = new JComboBox(Months);
		cboDay = new JComboBox();
		cboYear = new JComboBox();
		for (int i = 1; i <= 31; i++) {
			String days = "" + i;
			cboDay.addItem(days);
		}
		for (int i = 2000; i <= 2015; i++) {
			String years = "" + i;
			cboYear.addItem(years);
		}

		// Aligning The Date Option Controls.
		cboMonth.setBounds(105, 90, 92, 25);
		cboDay.setBounds(202, 90, 43, 25);
		cboYear.setBounds(250, 90, 60, 25);

		// Aligning The Buttons.
		btnSave = new JButton("Save");
		btnSave.setBounds(20, 165, 120, 25);
		btnSave.addActionListener(this);
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(185, 165, 120, 25);
		btnCancel.addActionListener(this);

		// Adding the All the Controls to Panel.
		jpInfo.add(lbNo);
		jpInfo.add(txtNo);
		jpInfo.add(lbName);
		jpInfo.add(txtName);
		jpInfo.add(lbDate);
		jpInfo.add(cboMonth);
		jpInfo.add(cboDay);
		jpInfo.add(cboYear);
		jpInfo.add(lbDeposit);
		jpInfo.add(txtDeposit);
		jpInfo.add(btnSave);
		jpInfo.add(btnCancel);

		// Adding Panel to Window.
		getContentPane().add(jpInfo);

		// In the End Showing the New Account Window.
		setVisible(true);

	}

	// Function use By Buttons of Window to Perform Action as User Click Them.
	public void actionPerformed(ActionEvent ae) {

		Object obj = ae.getSource();

		if (obj == btnSave) {
			if (txtNo.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide Id of Customer.", "BankSystem - EmptyField",
						JOptionPane.PLAIN_MESSAGE);
				txtNo.requestFocus();
			} else if (txtName.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide Name of Customer.", "BankSystem - EmptyField",
						JOptionPane.PLAIN_MESSAGE);
				txtName.requestFocus();
			} else if (txtDeposit.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide Deposit Amount.", "BankSystem - EmptyField",
						JOptionPane.PLAIN_MESSAGE);
				txtDeposit.requestFocus();
			} else {
				findRec(); // Finding if Account No. Already Exist or Not.
			}
		}
		if (obj == btnCancel) {
			txtClear();
			setVisible(false);
			dispose();
		}

	}

	void findRec() {
		if (CustomerRepository.getInstance().find(txtNo.getText()) != null) {
			JOptionPane.showMessageDialog(this, "Account No. " + txtNo.getText() + " is Already Exist.",
					"BankSystem - WrongNo", JOptionPane.PLAIN_MESSAGE);
			txtClear();
			return;
		}
		save();
	}

	void save() {
		if (CustomerRepository.getInstance()
				.Add(new CustomerRecord(txtNo.getText(), txtName.getText(),
						cboMonth.getSelectedItem() + " " + cboDay.getSelectedItem() + " " + cboYear.getSelectedItem(),
						Integer.parseInt(txtDeposit.getText())))) {
			JOptionPane.showMessageDialog(this, "The Record has been Saved Successfully", "BankSystem - Record Saved",
					JOptionPane.PLAIN_MESSAGE);
			txtClear();
		} else
			JOptionPane.showMessageDialog(this, "There are Some Problem with File", "BankSystem - Problem",
					JOptionPane.PLAIN_MESSAGE);
	}

	// Function use to Clear all TextFields of Window.
	void txtClear() {

		txtNo.setText("");
		txtName.setText("");
		txtDeposit.setText("");
		txtNo.requestFocus();

	}
}