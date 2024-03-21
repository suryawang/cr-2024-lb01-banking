import java.util.Calendar;
import java.util.Date;

public class Record {
	private String AccountNo;
	private String CustomerName;
	private String month;
	private int day;
	private int year;
	private int balance;

	public Record(String accountNo, String customerName, String month, int day, int year, int balance) {
		AccountNo = accountNo;
		CustomerName = customerName;
		this.month = month;
		this.day = day;
		this.year = year;
		this.balance = balance;
	}

	public static Record fromDb(String[] row) {
		return new Record(row[0], row[1], row[2], Integer.parseInt(row[3]), Integer.parseInt(row[4]),
				Integer.parseInt(row[5]));
	}

	public String getAccountNo() {
		return AccountNo;
	}

	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
