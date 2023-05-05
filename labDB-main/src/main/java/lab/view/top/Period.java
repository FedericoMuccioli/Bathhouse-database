package lab.view.top;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lab.db.Query;
import lab.view.center.Grid;
import lab.view.utilities.MyJDateChooser;

import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Period extends JPanel {
	private static final String NEW_STAGIONE = "  +  ";
	private final Query query;
	final JComboBox<String> annoField;
	final MyJDateChooser inizioCalendar;
	final MyJDateChooser fineCalendar;
	private final JLabel alert;
	
	public Period(final Query query, final Grid grid) {
		this.query = query;
		setLayout(new GridBagLayout());
		final var annoLabel = new JLabel("Stagione:");
		annoField = new JComboBox<String>();
		final var inizioLabel = new JLabel("inizio:");
		inizioCalendar = new MyJDateChooser(annoField);
		final var fineLabel = new JLabel("fine:");
		fineCalendar = new MyJDateChooser(annoField);
		final var button = new JButton("Aggiorna");
		alert = new JLabel("");
		updateStagioni();
		updateCalendar();
		
		annoField.addActionListener((e)->{
			String selected = (String) annoField.getSelectedItem();
			if(selected.equals(NEW_STAGIONE)) {
				new AddStagione(query);
				updateStagioni();
			}
			updateCalendar();
		});
		
		button.addActionListener(l -> {
			alert.setText("");
			var inizio = inizioCalendar.getDate();
			var fine = fineCalendar.getDate();
			if (inizio.compareTo(fine) > 0) {
				alert.setText("Dati inseriti non validi");
				return;
			}
			grid.updateGrid(Integer.parseInt((String) annoField.getSelectedItem()), inizio, fine);	
		});
		
		add(annoLabel);
		add(annoField);
		add(inizioLabel);
		add(inizioCalendar);
		add(fineLabel);
		add(fineCalendar);
		add(button);
		add(alert);
		button.doClick();
	}
	
	private void updateStagioni() {
		try {
			List<String> stagioni = query.getStagioni().stream().map(Object::toString).collect(Collectors.toList());
			stagioni.add(0, NEW_STAGIONE);
			annoField.setModel(new DefaultComboBoxModel<String>(stagioni.toArray(new String[0])));
			if (annoField.getItemCount() > 1) {
				annoField.setSelectedIndex(1);
			}
		} catch (SQLException e1) {
			alert.setText("Errore nel caricare le stagioni");
		}
	}
	
	private void updateCalendar() {
		String selected = (String) annoField.getSelectedItem();
		if (annoField.getItemCount() > 1 && !selected.equals(NEW_STAGIONE)) {
			int year = Integer.parseInt(selected);
			inizioCalendar.setYear(year);
			fineCalendar.setYear(year);
		}
	}

}
