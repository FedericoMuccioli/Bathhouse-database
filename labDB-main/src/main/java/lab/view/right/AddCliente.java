package lab.view.right;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.db.Query;
import lab.model.Cliente;
import lab.model.TipoCliente;
import lab.utils.Utils;

public class AddCliente extends JDialog {

	public AddCliente(final Query query) throws SQLException {
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
			try {
				Cliente cliente = new Cliente(codiceFiscale.getText(), nome.getText(), cognome.getText(), telefono.getText(), (TipoCliente) tipoCliente.getSelectedItem());
				query.insertCliente(cliente);
				alert.setText("Inserimento eseguito");
				Utils.closeJDialogAfterOneSecond(this);
			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
			}
		});

		var c = new GridBagConstraints();
		c.gridy = 0;
		panel.add(nomeLabel, c);
		panel.add(nome, c);
		c.gridy++;
		panel.add(cognomeLabel, c);
		panel.add(cognome, c);
		c.gridy++;
		panel.add(telefonoLabel, c);
		panel.add(telefono, c);
		c.gridy++;
		panel.add(tipoClienteLabel, c);
		panel.add(tipoCliente, c);
		c.gridy++;
		panel.add(codiceFiscaleLabel, c);
		panel.add(codiceFiscale, c);
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
