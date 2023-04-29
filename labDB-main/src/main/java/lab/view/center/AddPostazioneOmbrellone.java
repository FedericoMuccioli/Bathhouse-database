package lab.view.center;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lab.utils.Utils;

public class AddPostazioneOmbrellone extends JDialog {
	
	public AddPostazioneOmbrellone(final Connection connection, Grid grid, int anno, int fila, int colonna, Date inizio) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		final JTextField numeroOmbrellone = new JTextField("numeroOmbrellone");
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");
		
		button.addActionListener(l -> {
			final String query = "INSERT INTO PostazioniOmbrelloni (anno, numeroOmbrellone, dataInizio, fila, colonna)"
					+ " SELECT ?, ?, ?, ?, ? WHERE 100 >= (SELECT COUNT(*) FROM PostazioniOmbrelloni WHERE anno = ?)";
	        try (final PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, anno);
	            statement.setInt(2, Integer.parseInt(numeroOmbrellone.getText()));
	            statement.setDate(3, Utils.dateToSqlDate(inizio));
	            statement.setInt(4, fila);
	            statement.setInt(5, colonna);
	            statement.setInt(6, anno);
	            statement.executeUpdate();
	            alert.setText("Inserimento eseguito");
	            grid.updateGrid();
	        } catch (final Exception e) {
	        	alert.setText("Inserimento non eseguito");
	        }
		});
		
		panel.add(numeroOmbrellone);
		panel.add(button);
		var c = new GridBagConstraints();
		c.gridy=1;
		c.gridwidth = 10;
		panel.add(alert, c);
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
