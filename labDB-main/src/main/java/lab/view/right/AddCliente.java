package lab.view.right;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.db.Query;
import lab.model.TipoCliente;

public class AddCliente extends JDialog {

	public AddCliente(final Connection connection, Query query) throws SQLException {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		setTitle("Aggiungi Cliente");
		
		var codiceFiscaleLabel = new JLabel("Codice fiscale:"); 
		var nomeLabel = new JLabel("Nome:");
		var cognomeLabel = new JLabel("Cognome:");
		var tipoClienteLabel = new JLabel("Tipo cliente:");
		var telefonoLabel = new JLabel("Telefono*:");
		var codiceFiscale = new JTextField("codiceFiscale", 16);
		final JTextField nome = new JTextField("nome", 16);
		final JTextField cognome = new JTextField("cognome", 16);
		var tipoCliente = new JComboBox<TipoCliente>();
		final JTextField telefono = new JTextField("telefono", 16);
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");
		
		tipoCliente.setModel(new DefaultComboBoxModel<TipoCliente>(query.getTipiClienti().toArray(new TipoCliente[0])));
		tipoCliente.setPreferredSize(telefono.getPreferredSize());

		button.addActionListener(l -> {
			final String query2;
			if (telefono.getText().length() != 0) {
				query2 = "INSERT INTO Clienti VALUES (?,?,?,?,?)";
			} else {
				query2 = "INSERT INTO Clienti (codiceFiscale, nome, "
						+ "cognome, codiceTipoCliente) VALUES (?,?,?,?,?)";
			}
			try (final PreparedStatement statement = connection.prepareStatement(query2)) {
				statement.setString(1, codiceFiscale.getText());
				statement.setString(2, nome.getText());
				statement.setString(3, cognome.getText());
				if (telefono.getText().length() != 0) {
					statement.setString(4, telefono.getText());
					statement.setInt(5, ((TipoCliente) tipoCliente.getSelectedItem()).getCodiceUnivoco());
				}
				else {
					statement.setInt(4, ((TipoCliente) tipoCliente.getSelectedItem()).getCodiceUnivoco());
				}
				statement.executeUpdate();
				alert.setText("Inserimento eseguito");
			} catch (final Exception e) {
				e.printStackTrace();
				alert.setText("Inserimento non eseguito");
			}
		});

		//togliere y
		var c = new GridBagConstraints();
		int y = 0;
		panel.add(nomeLabel, c);
		panel.add(nome, c);
		c.gridy = ++y;
		panel.add(cognomeLabel, c);
		panel.add(cognome, c);
		c.gridy = ++y;
		panel.add(telefonoLabel, c);
		panel.add(telefono, c);
		c.gridy = ++y;
		panel.add(tipoClienteLabel, c);
		panel.add(tipoCliente, c);
		c.gridy = ++y;
		panel.add(codiceFiscaleLabel, c);
		panel.add(codiceFiscale, c);
		c.gridy = ++y;
		c.gridwidth = 2;
		panel.add(button, c);
		c.gridy = ++y;
		panel.add(alert, c);
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
