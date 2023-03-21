package lab.view.center;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lab.utils.Utils;

public class AddSedutaConPrenotazione extends JDialog {

	public AddSedutaConPrenotazione(final Connection connection, final int numeroSeduta, final int anno,
			final Date dataInizio, final Date dataFine) {

		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));

		final JTextField codiceFiscale = new JTextField("codiceFiscale Cliente");
		final JTextField prezzo = new JTextField("prezzo");
		final JTextField codiceUnivoco = new JTextField("codice Bagnino");
		final JTextField tipoSeduta = new JTextField("codice tipoSeduta");
		final var alert = new JLabel();
		final var button = new JButton("AFFITTA");
		
		button.addActionListener(l -> {
			final String query = "INSERT INTO SeduteConPrenotazioni VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			try (final PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, anno);
				statement.setInt(2, numeroSeduta);
				statement.setDate(3, Utils.dateToSqlDate(dataInizio));
				statement.setDate(4, Utils.dateToSqlDate(dataFine));
				statement.setDouble(5, Double.parseDouble(prezzo.getText()));
				statement.setInt(6, Integer.parseInt(tipoSeduta.getText()));
				statement.setString(7, codiceFiscale.getText());
				statement.setInt(8, Integer.parseInt(codiceUnivoco.getText()));
				statement.executeUpdate();
				alert.setText("Inserimento eseguito");
			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
			}
		});

		panel.add(codiceFiscale);
		panel.add(codiceUnivoco);
		panel.add(tipoSeduta);
		panel.add(prezzo);
		panel.add(button);
		var c = new GridBagConstraints();
		c.gridy=1;
		c.gridwidth = 7;
		panel.add(alert, c);
		add(panel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
