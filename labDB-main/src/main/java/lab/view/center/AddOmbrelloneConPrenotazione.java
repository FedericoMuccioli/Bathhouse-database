package lab.view.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.db.Query;
import lab.model.Dipendente;
import lab.model.Cliente;
import lab.utils.Utils;

public class AddOmbrelloneConPrenotazione extends JDialog {

	public AddOmbrelloneConPrenotazione(final Grid grid, final Query query, final int numeroOmbrellone, final int anno,
			final Date dataInizio, final Date dataFine) {
		var mainPanel = new JPanel(new BorderLayout());
		final var panel = new JPanel(new GridBagLayout());
		mainPanel.setPreferredSize(new Dimension(800,300));
		setTitle("Aggiungi prenotazione ombrellone");

		Integer[] sedute = {0, 1, 2, 3, 4};
		var dialog = this;
		var cliente = new JLabel("Cliente:");
		var codiceFiscale = new JButton("trova cliente");
		var bagninoLabel = new JLabel("Bagnino:");
		var bagnino = new JButton("trova bagnino");
		var lettiniLabel = new JLabel("Lettini:");
		var lettini = new JComboBox<Integer>(sedute);
		var sedieLabel = new JLabel("Sedie:");
		var sedie = new JComboBox<Integer>(sedute);
		var sdraioLabel = new JLabel("Sdraio:");
		var sdraio = new JComboBox<Integer>(sedute);
		var costo = new JLabel("Prezzo:");
		var prezzo = new JTextField("000,00", 16);
		var alert = new JLabel();
		var button = new JButton("AFFITTA");
		var removePostazioneOmbrellone = new JButton("Rimuovi postazione ombrellone in data: " + new SimpleDateFormat("dd/MM/yyyy").format(dataInizio));

		var prefSize = prezzo.getPreferredSize();
		codiceFiscale.setPreferredSize(prefSize);
		bagnino.setPreferredSize(prefSize);
		lettini.setPreferredSize(prefSize);
		sedie.setPreferredSize(prefSize);
		sdraio.setPreferredSize(prefSize);

		codiceFiscale.addActionListener(l -> {
			try {
				List<Cliente> clienti = query.getClienti();
				new FoundCliente(clienti, codiceFiscale);
			} catch (SQLException e) {
				alert.setText("Riprova a cercare il cliente");
				return;
			}	
		});

		bagnino.addActionListener(l -> {
			try {
				List<Dipendente> bagnini = query.getBagnini();
				new FoundBagnino(bagnini, bagnino);	
			} catch (SQLException e) {
				alert.setText("Riprova a cercare il cliente");
				return;
			}
		});

		button.addActionListener(l -> {
			try {
				int nLettini = (int) lettini.getSelectedItem();
				int nSedie = (int) sedie.getSelectedItem();
				int nSdraio = (int) sdraio.getSelectedItem();
				int n_sedute =  nLettini + nSedie + nSdraio;
				if (n_sedute <= 0 || n_sedute > 4) {
					throw new Exception();
				}
				if (!query.insertPrenotazioneOmbrellone(numeroOmbrellone, anno, dataInizio, dataFine, 
						Double.parseDouble(prezzo.getText()), codiceFiscale.getText(), Integer.parseInt(bagnino.getText()))) {
					throw new Exception();
				}
				if (nLettini > 0) {
					if(!query.insertLettini(numeroOmbrellone, anno, dataInizio, nLettini)){
						throw new Exception();
					}
				}
				if (nSedie > 0) {
					if(!query.insertSedie(numeroOmbrellone, anno, dataInizio, nSedie)){
						throw new Exception();
					}
				}
				if (nSdraio > 0) {
					if(!query.insertSdraio(numeroOmbrellone, anno, dataInizio, nSdraio)){
						throw new Exception();
					}
				}
				grid.updateGrid();
				alert.setText("Inserimento eseguito");
				Utils.closeJDialogAfterOneSecond(dialog);
			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
				return;
			}
		});

		removePostazioneOmbrellone.addActionListener(l -> {
			try {
				if (!query.removePostazioneOmbrellone(numeroOmbrellone, anno, new Date(dataInizio.getTime() - 86400000))) {
					throw new IllegalArgumentException();
				}
				grid.updateGrid();
				dispose();
			} catch (Exception e) {
				alert.setText("Giorno rimozione NON impostato");
			}
		});

		var c = new GridBagConstraints();
		int y = 0;
		c.gridy = y;
		panel.add(cliente, c);
		c.gridwidth = 3;
		panel.add(codiceFiscale, c);
		c.gridy = ++y;
		c.gridwidth = 1;
		panel.add(bagninoLabel, c);
		c.gridwidth = 3;
		panel.add(bagnino, c);
		c.gridy = ++y;
		c.gridwidth = 1;
		panel.add(lettiniLabel, c);
		panel.add(lettini, c);
		c.gridy = ++y;
		panel.add(sedieLabel, c);
		panel.add(sedie, c);
		c.gridy = ++y;
		panel.add(sdraioLabel, c);
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
		mainPanel.add(removePostazioneOmbrellone, BorderLayout.SOUTH);
		add(mainPanel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
