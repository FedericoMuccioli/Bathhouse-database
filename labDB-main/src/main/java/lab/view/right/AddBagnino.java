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
import lab.model.Bagnino;
import lab.utils.Utils;

public class AddBagnino extends JDialog {
	
	public AddBagnino(final Connection connection) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		final JTextField codiceFiscale = new JTextField("codiceFiscale");
		final JTextField nome = new JTextField("nome");
		final JTextField cognome = new JTextField("cognome");
		final JTextField codiceUnivoco = new JTextField("codiceUnivoco");
		final JTextField dataDiNascita = new JTextField("dd/MM/yyyy");
		final JTextField indirizzo = new JTextField("indirizzo");
		final JTextField telefono = new JTextField("telefono");
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");
		button.addActionListener(l -> {
			try {
				var bagnino = new Bagnino(codiceFiscale.getText(), nome.getText(), cognome.getText(), 
						Integer.parseInt(codiceUnivoco.getText()), new SimpleDateFormat("dd/MM/yyyy").parse(dataDiNascita.getText()), 
						indirizzo.getText(), telefono.getText());
				final String query = "INSERT INTO Bagnini VALUES (?,?,?,?,?,?,?)";
		        try (final PreparedStatement statement = connection.prepareStatement(query)) {
		            statement.setString(1, bagnino.getCodiceFiscale());
		            statement.setString(2, bagnino.getNome());
		            statement.setString(3, bagnino.getCognome());
		            statement.setInt(4, bagnino.getCodiceUnivoco());
		            statement.setDate(5, Utils.dateToSqlDate(bagnino.getDataDiNascita()));
		            statement.setString(6, bagnino.getIndirizzo());
		            statement.setString(7, bagnino.getTelefono());
		            statement.executeUpdate();
		            alert.setText("Inserimento eseguito");
		        } catch (final Exception e) {
		        	alert.setText("Inserimento non eseguito");
		        }
			} catch (Exception e) {
				alert.setText("Dati inseriti non correttamente");
			}
		});
		panel.add(codiceFiscale);
		panel.add(nome);
		panel.add(cognome);
		panel.add(codiceUnivoco);
		panel.add(dataDiNascita);
		panel.add(indirizzo);
		panel.add(telefono);
		panel.add(button);
		var c = new GridBagConstraints();
		c.gridy=1;
		c.gridwidth = 8;
		panel.add(alert, c);
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
