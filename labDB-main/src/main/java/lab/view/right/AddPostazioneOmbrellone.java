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
import lab.utils.Utils;

public class AddPostazioneOmbrellone extends JDialog {
	
	public AddPostazioneOmbrellone(final Connection connection) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		final JTextField anno = new JTextField("anno");
		final JTextField numeroOmbrellone = new JTextField("numeroOmbrellone");
		final JTextField fila = new JTextField("fila");
		final JTextField colonna = new JTextField("colonna");
		final JTextField dataInizio = new JTextField("I:dd/MM");
		final JTextField dataFine = new JTextField("F:dd/MM");
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");
		
		button.addActionListener(l -> {
			if (dataFine.getText().length() != 0) {
				final String query = "INSERT INTO PostazioniOmbrelloni SELECT ?, ?, ?, ?, ?, ? "
						+ "WHERE 100 >= (SELECT COUNT(*) FROM PostazioniOmbrelloni WHERE anno = ?)";
		        try (final PreparedStatement statement = connection.prepareStatement(query)) {
		            statement.setInt(1, Integer.parseInt(anno.getText()));
		            statement.setInt(2, Integer.parseInt(numeroOmbrellone.getText()));
		            statement.setDate(3, Utils.dateToSqlDate(new SimpleDateFormat("dd/MM/yyyy")
		            		.parse(dataInizio.getText() + "/" + anno.getText())));
		            statement.setDate(4, Utils.dateToSqlDate(new SimpleDateFormat("dd/MM/yyyy")
		            		.parse(dataFine.getText() + "/" + anno.getText())));
		            statement.setInt(5, Integer.parseInt(fila.getText()));
		            statement.setInt(6, Integer.parseInt(colonna.getText()));
		            statement.setInt(7, Integer.parseInt(anno.getText()));
		            statement.executeUpdate();
		            alert.setText("Inserimento eseguito");
		        } catch (final Exception e) {
		        	alert.setText("Inserimento non eseguito");
		        }
			} else {
				final String query = "INSERT INTO PostazioniOmbrelloni (anno, numeroOmbrellone, dataInizio, fila, colonna)"
						+ " SELECT ?, ?, ?, ?, ? WHERE 100 >= (SELECT COUNT(*) FROM PostazioniOmbrelloni WHERE anno = ?)";
		        try (final PreparedStatement statement = connection.prepareStatement(query)) {
		            statement.setInt(1, Integer.parseInt(anno.getText()));
		            statement.setInt(2, Integer.parseInt(numeroOmbrellone.getText()));
		            statement.setDate(3, Utils.dateToSqlDate(new SimpleDateFormat("dd/MM/yyyy")
		            		.parse(dataInizio.getText() + "/" + anno.getText())));
		            statement.setInt(4, Integer.parseInt(fila.getText()));
		            statement.setInt(5, Integer.parseInt(colonna.getText()));
		            statement.setInt(6, Integer.parseInt(anno.getText()));
		            statement.executeUpdate();
		            alert.setText("Inserimento eseguito");
		        } catch (final Exception e) {
		        	alert.setText("Inserimento non eseguito");
		        }
			}
		});
		
		panel.add(anno);
		panel.add(numeroOmbrellone);
		panel.add(fila);
		panel.add(colonna);
		panel.add(dataInizio);
		panel.add(dataFine);
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
