package lab.view.utilities;

import java.util.Date;

import javax.swing.JComboBox;

import com.toedter.calendar.JTextFieldDateEditor;

public class MyJTextFieldDateEditor extends JTextFieldDateEditor {

	private final JComboBox<String> annoSelection;
	
	public MyJTextFieldDateEditor(JComboBox<String> annoSelection) {
		super();
		this.annoSelection = annoSelection;
	}
	
	@Override
	public void setText(String t) {
		super.setText(deleteYear(t));
	}
	
	@Override
	public String getText() {
		return super.getText().isBlank() ? 
			deleteYear(dateFormatter.format(new Date())) + " " + annoSelection.getSelectedItem() 
			: super.getText() + " " + annoSelection.getSelectedItem();
	}
	
	private String deleteYear(String t) {
		if(t.length() > 5) {
		    t = t.substring(0, t.length() - 5);
		}
		return t;
	}
	
}
