package lab.view.right;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import lab.db.Query;
import lab.model.Bagnino;
import lab.utils.Utils;
import lab.view.utilities.MyJDateChooser;

public class AddBagnino extends JDialog {

	public AddBagnino(final Query query) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		setTitle("Aggiungi Bagnino");
		
		var codiceFiscaleLabel = new JLabel("Codice fiscale:"); 
		var codiceFiscale = new JTextField("codiceFiscale", 16);
		var nomeLabel = new JLabel("Nome:");
		var nome = new JTextField("nome", 16);
		var cognomeLabel = new JLabel("Cognome:");
		var cognome = new JTextField("cognome", 16);
		var telefonoLabel = new JLabel("Telefono:");
		var telefono = new JTextField("telefono", 16);
		var dataNascitaLabel = new JLabel("Data di nascita:");
		var dataNascita = new JDateChooser();
		dataNascita.setPreferredSize(nome.getPreferredSize());
		var indirizzoLabel = new JLabel("Indirizzo:");
		var indirizzo = new JTextField("indirizzo", 16);
		var button = new JButton("AGGIUNGI");
		var alert = new JLabel();

		button.addActionListener(l -> {

			try {
				var bagnino = new Bagnino(codiceFiscale.getText(), nome.getText(), cognome.getText(), 
						dataNascita.getDate(), indirizzo.getText(), telefono.getText());
				if (!query.insertBagnino(bagnino)) {
					throw new Exception();
				}
				alert.setText("Inserimento eseguito");
				Utils.closeJDialogAfterOneSecond(this);

			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
			}
		});

		var c = new GridBagConstraints();
		c.gridy++;
		panel.add(nomeLabel, c);
		panel.add(nome, c);
		c.gridy++;
		panel.add(cognomeLabel, c);
		panel.add(cognome, c);
		c.gridy++;
		panel.add(indirizzoLabel, c);
		panel.add(indirizzo, c);
		c.gridy++;
		panel.add(telefonoLabel, c);
		panel.add(telefono, c);
		c.gridy++;
		panel.add(codiceFiscaleLabel, c);
		panel.add(codiceFiscale, c);
		c.gridy++;
		panel.add(dataNascitaLabel, c);
		panel.add(dataNascita, c);
		c.gridy++;
		c.gridwidth = 2;
		panel.add(button, c);
		c.gridy++;
		panel.add(alert, c);
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
