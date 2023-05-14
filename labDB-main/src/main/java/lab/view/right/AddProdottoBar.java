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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import lab.db.Query;
import lab.model.Cliente;
import lab.model.TipoCliente;
import lab.utils.Utils;

public class AddProdottoBar extends JDialog {

	public AddProdottoBar(final Query query) throws SQLException {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		setTitle("Aggiungi Prodotto Bar");

		var nomeLabel = new JLabel("Nome:");
		var descrizioneLabel = new JLabel("Descrizione:");
		var prezzoLabel = new JLabel("Prezzo:");
		var tipoLabel = new JLabel("Tipo:");
		var nome = new JTextField("nome", 16);
		var descrizione = new JTextArea();
		var tipoProdotto = new JComboBox<TipoCliente>();
		var prezzo = new JTextField("telefono", 16);
		var alert = new JLabel();
		var button = new JButton("AGGIUNGI");

		tipoProdotto.setModel(new DefaultComboBoxModel<TipoCliente>(query.getTipiClienti().toArray(new TipoCliente[0])));
		tipoProdotto.setPreferredSize(prezzo.getPreferredSize());

//		button.addActionListener(l -> {
//			try {
//				Cliente cliente = new Cliente(codiceFiscale.getText(), nome.getText(), descrizione.getText(), prezzo.getText(), (TipoCliente) tipoProdotto.getSelectedItem());
//				query.insertCliente(cliente);
//				alert.setText("Inserimento eseguito");
//				Utils.closeJDialogAfterOneSecond(this);
//			} catch (final Exception e) {
//				alert.setText("Inserimento non eseguito");
//			}
//		});

		var c = new GridBagConstraints();
		c.gridy = 0;
		panel.add(nomeLabel, c);
		panel.add(nome, c);
		c.gridy++;
		panel.add(descrizioneLabel, c);
		panel.add(descrizione, c);
		c.gridy++;
		panel.add(tipoLabel, c);
		panel.add(prezzo, c);
		c.gridy++;
		panel.add(prezzoLabel, c);
		panel.add(tipoProdotto, c);
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
