package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerRecord {
	private String account_no;
	private String name;
	private Date date;
	private int deposit;

	public CustomerRecord(String account_no, String name, Date date, int deposit) {
		this.account_no = account_no;
		this.name = name;
		this.date = date;
		this.deposit = deposit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public String getAccount_no() {
		return account_no;
	}

	public String getName() {
		return name;
	}

	public String getMonth() {
		var s = new SimpleDateFormat("MMMM");
		return s.format(date);
	}

	public String getDay() {
		var s = new SimpleDateFormat("dd");
		return s.format(date);
	}

	public String getYear() {
		var s = new SimpleDateFormat("yyyy");
		return s.format(date);
	}
}