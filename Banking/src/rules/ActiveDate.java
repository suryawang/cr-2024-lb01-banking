package rules;

import javax.swing.JComboBox;

public final class ActiveDate {
	public static void fillOption(JComboBox month, JComboBox day, JComboBox year) {
		String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		month.removeAllItems();
		day.removeAllItems();
		year.removeAllItems();
		for (var i : months)
			month.addItem(i);
		for (int i = 1; i <= 31; i++)
			day.addItem(i + "");
		for (int i = 2000; i <= 2024; i++)
			year.addItem(i + "");
	}
}
