package lab.view.top;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lab.view.center.Grid;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Period extends JPanel {
	
	public Period(final Grid grid) {
		setLayout(new GridBagLayout());
		final var annoLabel = new JLabel("ANNO:");
		final var annoField = new JTextField("2022");
		final var inizioLabel = new JLabel("inizio:");
		final var inizioField = new JTextField("dd/MM");
		final var fineLabel = new JLabel("fine:");
		final var fineField = new JTextField("dd/MM");
		final var button = new JButton("Aggiorna");
		final var alert = new JLabel("");
		
		button.addActionListener(l -> {
			alert.setText("");
			final Integer anno = Integer.parseInt(annoField.getText());
			final Date inizio;
			final Date fine;
			try {
				inizio = new SimpleDateFormat("dd/MM/yyyy").parse(inizioField.getText() + "/" + annoField.getText());
				fine = new SimpleDateFormat("dd/MM/yyyy").parse(fineField.getText() + "/" + annoField.getText());
				if (inizio.compareTo(fine) > 0) {
					throw new IllegalStateException();
				}
			} catch (Exception e) {
				alert.setText("Dati inseriti non validi");
				return;
			}
			grid.updateGrid(anno, inizio, fine);	
		});
		
		add(annoLabel);
		add(annoField);
		add(inizioLabel);
		add(inizioField);
		add(fineLabel);
		add(fineField);
		add(button);
		add(alert);
	}

}
