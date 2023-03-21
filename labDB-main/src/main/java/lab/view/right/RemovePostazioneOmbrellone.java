package lab.view.right;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.utils.Utils;

public class RemovePostazioneOmbrellone extends JDialog {

	public RemovePostazioneOmbrellone(final Connection connection) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		final JTextField anno = new JTextField("anno");
		final JTextField numeroOmbrellone = new JTextField("numeroOmbrellone");
		final JTextField dataFine = new JTextField("dd/MM");
		final JLabel alert = new JLabel();
		final var button = new JButton("RIMUOVI");
		
		button.addActionListener(l -> {
			dataFine.setText(dataFine.getText() + "/" + anno.getText());
			final String query = "UPDATE PostazioniOmbrelloni SET dataFine = ? WHERE anno = ? AND numeroOmbrellone = ?";
			try (final PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setDate(1, Utils.dateToSqlDate(new SimpleDateFormat("dd/MM/yyyy").parse(dataFine.getText())));
				statement.setInt(2, Integer.parseInt(anno.getText()));
				statement.setInt(3, Integer.parseInt(numeroOmbrellone.getText()));
				statement.executeUpdate();
				alert.setText("Rimozione eseguita");
			} catch (final Exception e) {
				alert.setText("Rimozione non eseguita");
			}
		});

		panel.add(anno);
		panel.add(numeroOmbrellone);
		panel.add(dataFine);
		panel.add(button);
		var c = new GridBagConstraints();
		c.gridy=1;
		c.gridwidth = 10;
		panel.add(alert, c);
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
