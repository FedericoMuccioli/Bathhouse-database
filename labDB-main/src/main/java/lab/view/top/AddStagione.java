package lab.view.top;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.db.Query;

public class AddStagione extends JDialog {

	public AddStagione(Query query) {
		setModal(true);
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		final JTextField anno = new JTextField("anno");
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");
		button.addActionListener(l -> {
			try {
				query.addStagione(Integer.parseInt(anno.getText()));
				alert.setText("Inserimento eseguito");
			} catch (final Exception e) {
				alert.setText("Inserimento non eseguito");
			}
		});
		panel.add(anno);
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
