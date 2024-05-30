package repository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.CustomerRecord;

public class CustomerRepository {
	private static CustomerRepository _instance = new CustomerRepository();

	public static CustomerRepository getInstance() {
		return _instance;
	}

	private CustomerRepository() {
		populate();
	}

	// String Type Array use to Load Records From File.
	private Vector<CustomerRecord> records = new Vector<CustomerRecord>();

	private void populate() {
		records = new Vector<CustomerRecord>();
		try {
			var fis = new FileInputStream("Bank.dat");
			var dis = new DataInputStream(fis);
			try {
				var d = new String[6];
				while (true) {
					for (int i = 0; i < 6; i++)
						d[i] = dis.readUTF();
					var s = new SimpleDateFormat("MMMM dd yyyy");
					var date = s.parse(d[2] + " " + d[3] + " " + d[4]);

					records.add(new CustomerRecord(d[0], d[1], date, Integer.parseInt(d[5])));
				}
			} catch (Exception ex) {
			}
			dis.close();
			fis.close();

		} catch (Exception x) {
		}
	}

	public boolean add(CustomerRecord item) {
		try {
			var fos = new FileOutputStream("Bank.dat", true);
			var dos = new DataOutputStream(fos);

			dos.writeUTF(item.getAccount_no());
			dos.writeUTF(item.getName());
			dos.writeUTF(item.getMonth());
			dos.writeUTF(item.getDay());
			dos.writeUTF(item.getYear());
			dos.writeUTF(item.getDeposit() + "");

			records.add(item);
			dos.close();
			fos.close();
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}

	public boolean remove(CustomerRecord item) {
		if (records.remove(item))
			return save();
		return false;
	}

	public boolean save() {
		try {
			var fos = new FileOutputStream("Bank.dat");
			var dos = new DataOutputStream(fos);
			for (var item : records) {
				dos.writeUTF(item.getAccount_no());
				dos.writeUTF(item.getName());
				dos.writeUTF(item.getMonth());
				dos.writeUTF(item.getDay());
				dos.writeUTF(item.getYear());
				dos.writeUTF(item.getDeposit() + "");
			}

			dos.close();
			fos.close();
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}

	public CustomerRecord find(String account_no) {
		for (var it : records)
			if (it.getAccount_no().equals(account_no))
				return it;
		return null;
	}
}
