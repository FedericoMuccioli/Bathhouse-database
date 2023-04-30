package lab.view.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.db.Query;
import lab.utils.Utils;
//import lab.view.right.RemovePostazioneOmbrellone;

public class AddOmbrelloneConPrenotazione extends JDialog {

	private final Connection connection;
	private final int numeroOmbrellone;
	private final int anno;
	private final Date dataInizio;
	private final JLabel alert;


	public AddOmbrelloneConPrenotazione(Grid grid, final Connection connection, Query query, final int numeroOmbrellone, final int anno,
			final Date dataInizio, final Date dataFine) {
		this.connection = connection;
		this.numeroOmbrellone = numeroOmbrellone;
		this.anno = anno;
		this.dataInizio = dataInizio;
		var mainPanel = new JPanel(new BorderLayout());
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));

		var cliente = new JLabel("Cliente:");
		final JTextField codiceFiscale = new JTextField(16);
		var bagnino = new JLabel("Bagnino:");
		final JTextField codiceUnivoco = new JTextField(16);
		var sedute = new JLabel("Sedute:");
		final JTextField lettini = new JTextField("n lettini");
		final JTextField sedie = new JTextField("n sedie");
		final JTextField sdraio = new JTextField("n sdraio");
		var costo = new JLabel("Costo:");
		final JTextField prezzo = new JTextField(16);
		this.alert = new JLabel();
		var removePostazioneOmbrellone = new JButton("Imposta giorno rimozione postazione ombrellone");
		final var button = new JButton("AFFITTA");
		codiceFiscale.setText("codiceFiscale");
		codiceUnivoco.setText("codice Bagnino");
		prezzo.setText("000,00");
	
		removePostazioneOmbrellone.addActionListener(l -> {
			try {
				if (!query.removePostazioneOmbrellone(numeroOmbrellone, anno, dataFine)) {
					throw new IllegalArgumentException();
				}
				grid.updateGrid();
				alert.setText("Giorno rimozione impostato");
			} catch (Exception e) {
				alert.setText("Giorno rimozione NON impostato");
			}
		});
		
		button.addActionListener(l -> {
			//Inserimento OmbrelloneConPrenotazione
			final String queryS = "INSERT INTO OmbrelloniConPrenotazione VALUES (?, ?, ?, ?, ?, ?, ?)";
			try (final PreparedStatement statement = connection.prepareStatement(queryS)) {
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
				return;
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
		var c = new GridBagConstraints();
		int y = 0;
		c.gridy = y;
		panel.add(cliente, c);
		c.gridwidth = 3;
		panel.add(codiceFiscale, c);
		c.gridy = ++y;
		c.gridwidth = 1;
		panel.add(bagnino, c);
		c.gridwidth = 3;
		panel.add(codiceUnivoco, c);
		c.gridy = ++y;
		c.gridwidth = 1;
		panel.add(sedute, c);
		panel.add(lettini, c);
		panel.add(sedie, c);
		panel.add(sdraio, c);
		c.gridy = ++y;
		panel.add(costo, c);
		c.gridwidth = 3;
		panel.add(prezzo, c);
		c.gridy = ++y;
		c.gridwidth = 4;
		panel.add(button, c);
		c.gridy=++y;
		panel.add(alert, c);
		mainPanel.add(panel);
		if (dataInizio.equals(dataFine)) {
			mainPanel.add(removePostazioneOmbrellone, BorderLayout.SOUTH);
		}
		add(mainPanel);
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
