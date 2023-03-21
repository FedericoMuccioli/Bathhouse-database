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

public class AddOmbrellone extends JDialog {

	public AddOmbrellone(final Connection connection) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		final JTextField nOmbrellone = new JTextField("nOmbrellone");
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");
		
		button.addActionListener(l -> {
			try {
				final String query = "INSERT INTO Ombrelloni VALUES (?)";
		        try (final PreparedStatement statement = connection.prepareStatement(query)) {
		            statement.setInt(1, Integer.parseInt(nOmbrellone.getText()));
		            statement.executeUpdate();
		            alert.setText("Inserimento eseguito");
		        } catch (final Exception e) {
		        	alert.setText("Inserimento non eseguito");
		        }
			} catch (Exception e) {
				alert.setText("Dati inseriti non correttamente");
			}
		});
		
		panel.add(nOmbrellone);
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
