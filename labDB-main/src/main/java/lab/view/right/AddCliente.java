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

public class AddCliente extends JDialog {

	public AddCliente(final Connection connection) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		final JTextField codiceFiscale = new JTextField("codiceFiscale");
		final JTextField nome = new JTextField("nome");
		final JTextField cognome = new JTextField("cognome");
		final JTextField nominativo = new JTextField("nominativo");
		final JTextField tipoCliente = new JTextField("codiceTipoCliente");
		final JTextField telefono = new JTextField("telefono");
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");

		button.addActionListener(l -> {
			final String query;
			if (telefono.getText().length() != 0) {
				query = "INSERT INTO Clienti VALUES (?,?,?,?,?,?)";
			} else {
				query = "INSERT INTO Clienti (codiceFiscale, nome, "
						+ "cognome, nominativo, codiceTipoCliente) VALUES (?,?,?,?,?)";
			}
			try (final PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, codiceFiscale.getText());
				statement.setString(2, nome.getText());
				statement.setString(3, cognome.getText());
				statement.setString(4, nominativo.getText());
				if (telefono.getText().length() != 0) {
					statement.setString(5, telefono.getText());
					statement.setInt(6, Integer.parseInt(tipoCliente.getText()));
				}
				else {
					statement.setInt(5, Integer.parseInt(tipoCliente.getText()));
				}
				statement.executeUpdate();
				alert.setText("Inserimento eseguito");
			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
			}
		});

		panel.add(nome);
		panel.add(cognome);
		panel.add(nominativo);
		panel.add(tipoCliente);
		panel.add(telefono);
		panel.add(codiceFiscale);
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
