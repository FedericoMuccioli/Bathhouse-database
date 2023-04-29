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

public class AddOmbrelloneConPrenotazione extends JDialog {

	private final Connection connection;
	private final int numeroOmbrellone;
	private final int anno;
	private final Date dataInizio;
	private final JLabel alert;


	public AddOmbrelloneConPrenotazione(Grid grid, final Connection connection, final int numeroOmbrellone, final int anno,
			final Date dataInizio, final Date dataFine) {
		this.connection = connection;
		this.numeroOmbrellone = numeroOmbrellone;
		this.anno = anno;
		this.dataInizio = dataInizio;
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));

		final JTextField codiceFiscale = new JTextField("codiceFiscale Cliente");
		final JTextField prezzo = new JTextField("prezzo");
		final JTextField codiceUnivoco = new JTextField("codice Bagnino");
		final JTextField lettini = new JTextField("n lettini");
		final JTextField sedie = new JTextField("n sedie");
		final JTextField sdraio = new JTextField("n sdraio");
		this.alert = new JLabel();
		final var button = new JButton("AFFITTA");
		button.addActionListener(l -> {
			//Inserimento OmbrelloneConPrenotazione
			final String query = "INSERT INTO OmbrelloniConPrenotazione VALUES (?, ?, ?, ?, ?, ?, ?)";
			try (final PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, numeroOmbrellone);
				statement.setInt(2, anno);
				statement.setDate(3, Utils.dateToSqlDate(dataInizio));
				statement.setDate(4, Utils.dateToSqlDate(dataFine));
				statement.setDouble(5, Double.parseDouble(prezzo.getText()));
				statement.setString(6, codiceFiscale.getText());
				statement.setInt(7, Integer.parseInt(codiceUnivoco.getText()));
				statement.executeUpdate();
				alert.setText("Inserimento eseguito");
			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
			}
			//Inserimento Composizione
			int n_lettini = Integer.parseInt(lettini.getText());
			int n_sedie = Integer.parseInt(sedie.getText());
			int n_sdraio = Integer.parseInt(sdraio.getText());
			int n_sedute = n_lettini + n_sedie + n_sdraio;
			if (n_sedute == 0 || n_sedute > 4) {
				alert.setText("Dati inseriti non correttamente");
				return;
			}
			inserimentoComposizione(1, n_lettini);
			inserimentoComposizione(2, n_sedie);
			inserimentoComposizione(3, n_sdraio);
			grid.updateGrid();

		});
		panel.add(codiceFiscale);
		panel.add(codiceUnivoco);
		panel.add(lettini);
		panel.add(sedie);
		panel.add(sdraio);
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

	private void inserimentoComposizione(final int codiceSeduta, final int quantità) {
		if (quantità <= 0) {
			return;
		}
		final String query = "INSERT INTO Composizioni VALUES (?, ?, ?, ?, ?)";
		try (final PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, numeroOmbrellone);
			statement.setInt(2, anno);
			statement.setDate(3, Utils.dateToSqlDate(dataInizio));
			statement.setInt(4, codiceSeduta);
			statement.setInt(5, quantità);
			statement.executeUpdate();
			alert.setText("Inserimento eseguito");
		} catch (final Exception e) {
			alert.setText("Inserimento non eseguito");
		}
	}

}
