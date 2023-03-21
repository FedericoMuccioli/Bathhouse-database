package lab.view.center;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import lab.utils.Utils;

public class VisualSedutaConPrenotazione extends JDialog {
	
	public VisualSedutaConPrenotazione(final Connection connection, final int numeroSeduta, final int anno, 
			final Date dataInizio, final Date dataFine) throws SQLException {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		
		final var conferma = new JButton("Conferma");
		final JLabel output = new JLabel();
		final List<String> affitti = new ArrayList<>();
		final JList<String> affittiJList;	
		
		final String query = "SELECT nominativo, dataInizio, dataFine FROM SeduteConPrenotazioni O JOIN Clienti C "
				+ "ON (O.codiceFiscaleCliente = C.codiceFiscale) WHERE anno = ? "
				+ "AND numeroSeduta = ? AND NOT ((dataInizio < ? AND dataFine < ?) OR (dataInizio > ? AND dataFine > ?))";
		try (final PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, anno);
        	statement.setInt(2, numeroSeduta);
        	statement.setDate(3, Utils.dateToSqlDate(dataInizio));
        	statement.setDate(4, Utils.dateToSqlDate(dataInizio));
        	statement.setDate(5, Utils.dateToSqlDate(dataFine));
        	statement.setDate(6, Utils.dateToSqlDate(dataFine));
        	final ResultSet resultSet = statement.executeQuery();
        	
        	while(resultSet.next()) {
        		affitti.add(resultSet.getString("nominativo") + ", " + resultSet.getString("dataInizio") + ", " + resultSet.getString("dataFine"));
        	}
        }
		affittiJList = new JList(affitti.toArray());
		
		conferma.addActionListener(l -> {
			final var selectAffitto = affittiJList.getSelectedValue();
			if (selectAffitto != null) {
				final var values = selectAffitto.split(", ");
				final var comand = "SELECT O.prezzo, Cl.codiceFiscale, O.codiceUnivocoBagnino, O.codiceTipoSeduta, Cl.nome, "
						+ "Cl.cognome, Cl.telefono, codiceTipoCliente FROM SeduteConPrenotazioni O JOIN Clienti Cl "
						+ "ON (O.codiceFiscaleCliente = Cl.codiceFiscale) WHERE O.anno = ? AND O.numeroSeduta = ? AND O.dataInizio = ?";
				try (final PreparedStatement statement = connection.prepareStatement(comand)) {
		        	statement.setInt(1, anno);
		        	statement.setInt(2, numeroSeduta);
		        	statement.setDate(3, java.sql.Date.valueOf(values[1]));
		        	final ResultSet resultSet = statement.executeQuery();
		        	String descrizione;
		        	if (resultSet.next()) {
		        		descrizione = "<html>nSeduta: " + numeroSeduta + ", tipoSeduta: " + resultSet.getInt("codiceTipoSeduta") + "<br>anno: " + anno + ", inizio: " + values[1] + ", fine: " + values[2]
		        				+ ", prezzo: " + resultSet.getString("prezzo") + ", codBagnino: " + resultSet.getInt("codiceUnivocoBagnino") 
		        				+ "<br>nome: " + resultSet.getString("nome") + ", cognome: " + resultSet.getString("cognome")
		        				+ ", nominativo: " + values[0] + "<br> telefono: " + resultSet.getString("telefono") + ", codTipoCliente: "
		        				+ resultSet.getInt("codiceTipoCliente") + ", CF: " + resultSet.getString("codiceFiscale") + "</html>";
			        	output.setText(descrizione);
		        	}
		        } catch (final Exception e) {
		        	throw new IllegalStateException(e);
		        }
			}
		});
		
		panel.add(affittiJList);
		panel.add(conferma);
		var c = new GridBagConstraints();
		c.gridy = 1;
		c.gridwidth = 3;
		panel.add(output, c);
		add(panel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
