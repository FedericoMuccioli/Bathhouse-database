package lab.view.utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

public class MyJDateChooser extends JDateChooser {
	
	public MyJDateChooser(JComboBox<String> annoSelection) {
		super(new MyJTextFieldDateEditor(annoSelection));
		getJCalendar().getYearChooser().setVisible(false);
	}
	
	public void setYear(int year) {
		setMinSelectableDate(new GregorianCalendar(year, 0, 1).getTime());
		setMaxSelectableDate(new GregorianCalendar(year, 11, 31).getTime());	
	}
	
}
