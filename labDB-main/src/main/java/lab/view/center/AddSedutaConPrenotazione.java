package lab.view.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.db.Query;
import lab.model.Bagnino;
import lab.model.Cliente;
import lab.model.TipoSeduta;
import lab.utils.Utils;

public class AddSedutaConPrenotazione extends JDialog {

	public AddSedutaConPrenotazione(final Grid grid, final Query query, final int numeroSeduta, final int anno,
			final Date dataInizio, final Date dataFine) {
		var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		setTitle("Aggiungi prenotazione seduta");

		var cliente = new JLabel("Cliente:");
		var codiceFiscale = new JButton("trova cliente");
		var bagninoLabel = new JLabel("Bagnino:");
		var bagnino = new JButton("trova bagnino");
		var tipoSedutaLabel = new JLabel("Seduta:");
		var tipoSeduta = new JComboBox<TipoSeduta>();
		var costo = new JLabel("Prezzo:");
		var prezzo = new JTextField("000.00", 16);
		var alert = new JLabel();
		var button = new JButton("AFFITTA");
		var prefSize = prezzo.getPreferredSize();
		codiceFiscale.setPreferredSize(prefSize);
		bagnino.setPreferredSize(prefSize);
		tipoSeduta.setPreferredSize(prefSize);

		try {
			var tipiSedute = query.getTipiSedute();
			tipoSeduta.setModel(new DefaultComboBoxModel<TipoSeduta>(tipiSedute.toArray(new TipoSeduta[0])));
		} catch (Exception e) {
			alert.setText("Errore nella lettura dei tipi di sedute");
			return;
		}

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
				List<Bagnino> bagnini = query.getBagnini();
				new FoundBagnino(bagnini, bagnino);	
			} catch (SQLException e) {
				alert.setText("Riprova a cercare il bagnino");
				return;
			}
		});

		button.addActionListener(l -> {
			try {
				query.insertPrenotazioneSeduta(numeroSeduta, anno, dataInizio, dataFine, Double.parseDouble(prezzo.getText()), ((TipoSeduta)tipoSeduta.getSelectedItem()).getCodTipoSeduta(), codiceFiscale.getText(), Integer.parseInt(bagnino.getText()));
				grid.updateGrid();
				alert.setText("Inserimento eseguito");
				Utils.closeJDialogAfterOneSecond(this);
			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
			}
		});

		var c = new GridBagConstraints();
		int y = 0;
		c.gridy = y;
		panel.add(cliente, c);
		panel.add(codiceFiscale, c);
		c.gridy = ++y;
		panel.add(bagninoLabel, c);
		panel.add(bagnino, c);
		c.gridy = ++y;
		panel.add(tipoSedutaLabel, c);
		panel.add(tipoSeduta, c);
		c.gridy = ++y;
		panel.add(costo, c);
		panel.add(prezzo, c);
		c.gridy = ++y;
		c.gridwidth = 2;
		panel.add(button, c);
		c.gridy=++y;
		panel.add(alert, c);

		add(panel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
