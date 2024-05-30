import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.CustomerRecord;
import repository.CustomerRepository;
import rules.ActiveDate;

import java.io.*;

public class DepositMoney extends JInternalFrame implements ActionListener {

	private JPanel jpDep = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbDeposit;
	private JTextField txtNo, txtName, txtDeposit;
	private JComboBox cboMonth, cboDay, cboYear;
	private JButton btnSave, btnCancel;

	DepositMoney() {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("Deposit Money", false, true, false, true);
		setSize(335, 235);

		jpDep.setLayout(null);

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
		txtName.setEnabled(false);
		txtName.setBounds(105, 55, 205, 25);
		txtDeposit = new JTextField();
		txtDeposit.setHorizontalAlignment(JTextField.RIGHT);
		txtDeposit.setBounds(105, 125, 205, 25);

		cboMonth = new JComboBox();
		cboDay = new JComboBox();
		cboYear = new JComboBox();
		ActiveDate.fillOption(cboMonth, cboDay, cboYear);

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
		// Checking the Accunt No. Provided By User on Lost Focus of the TextBox.
		txtNo.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent fe) {
				if (txtNo.getText().equals("")) {
				} else {
					findRec(); // Finding if Account No. Already Exist or Not.
				}
			}
		});

		// Adding the All the Controls to Panel.
		jpDep.add(lbNo);
		jpDep.add(txtNo);
		jpDep.add(lbName);
		jpDep.add(txtName);
		jpDep.add(lbDate);
		jpDep.add(cboMonth);
		jpDep.add(cboDay);
		jpDep.add(cboYear);
		jpDep.add(lbDeposit);
		jpDep.add(txtDeposit);
		jpDep.add(btnSave);
		jpDep.add(btnCancel);

		// Adding Panel to Window.
		getContentPane().add(jpDep);

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
			} else if (txtDeposit.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide Deposit Amount.", "BankSystem - EmptyField",
						JOptionPane.PLAIN_MESSAGE);
				txtDeposit.requestFocus();
			} else {
				editRec(); // Update the Contents of Array.
			}
		}
		if (obj == btnCancel) {
			txtClear();
			setVisible(false);
			dispose();
		}

	}

	void findRec() {
		var rec = CustomerRepository.getInstance().find(txtNo.getText());
		if (rec != null)
			showRec(rec);
		else {
			String str = txtNo.getText();
			txtClear();
			JOptionPane.showMessageDialog(this, "Account No. " + str + " doesn't Exist.", "BankSystem - WrongNo",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	CustomerRecord current;

	void showRec(CustomerRecord item) {
		txtNo.setText(item.getAccount_no());
		txtName.setText(item.getName());
		current = item;
	}

	// Function use to Clear all TextFields of Window.
	void txtClear() {

		txtNo.setText("");
		txtName.setText("");
		txtDeposit.setText("");
		txtNo.requestFocus();

	}

	// Function use to Edit an Element's Value of the Array.
	public void editRec() {

		current.setDate(cboMonth.getSelectedItem() + " " + cboDay.getSelectedItem() + " " + cboYear.getSelectedItem());
		current.setDeposit(current.getDeposit() + Integer.parseInt(txtDeposit.getText()));
		if (CustomerRepository.getInstance().save()) {
			JOptionPane.showMessageDialog(this, "The File is Updated Successfully", "BankSystem - Record Saved",
					JOptionPane.PLAIN_MESSAGE);
			txtClear();
		} else
			JOptionPane.showMessageDialog(this, "There are Some Problem with File", "BankSystem - Problem",
					JOptionPane.PLAIN_MESSAGE);
	}

	// Function use to Lock all Buttons of Window.
	void btnEnable() {

		txtNo.setEnabled(false);
		cboMonth.setEnabled(false);
		cboDay.setEnabled(false);
		cboYear.setEnabled(false);
		txtDeposit.setEnabled(false);
		btnSave.setEnabled(false);

	}

}