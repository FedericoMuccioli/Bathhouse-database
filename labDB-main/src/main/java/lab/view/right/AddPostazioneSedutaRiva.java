package lab.view.right;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPostazioneSedutaRiva extends JDialog {

	public AddPostazioneSedutaRiva(final Connection connection) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		final JTextField anno = new JTextField("anno");
		final JTextField numeroOmbrellone = new JTextField("numeroSeduta");
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");

		button.addActionListener(l -> {
			final String query = "INSERT INTO PostazioniSeduteRiva VALUES (?, ?)";
			try (final PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, Integer.parseInt(anno.getText()));
				statement.setInt(2, Integer.parseInt(numeroOmbrellone.getText()));
				statement.executeUpdate();
				alert.setText("Inserimento eseguito");
			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
			}
		});
		panel.add(anno);
		panel.add(numeroOmbrellone);
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
