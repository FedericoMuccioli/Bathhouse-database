package lab.view.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import lab.db.Query;
import lab.model.Cliente;
import lab.utils.Utils;
import lab.view.utilities.MyDefaultTableCellRenderer;

public class VisualOmbrelloneConPrenotazione extends JDialog {
	
	//TOGLIERE THROWS
	public VisualOmbrelloneConPrenotazione(final Connection connection, Query query, final int numeroOmbrellone, final int anno, 
			final Date dataInizio, final Date dataFine) throws SQLException {
		var panel = new JPanel(new BorderLayout());
//		panel.setPreferredSize(new Dimension(800,300));
		panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setTitle("Prenotazioni");
		
		var ombrelloniConPrenotazioni = query.getOmbrelloniConPrenotazioni(anno, numeroOmbrellone, dataInizio, dataFine);
		
		
		var tableModel = new DefaultTableModel();
		var table = new JTable(tableModel);
		table.getTableHeader().setDefaultRenderer(new MyDefaultTableCellRenderer());
		tableModel.addColumn("dataInizio");
		tableModel.addColumn("dataFine");
		tableModel.addColumn("lettini");
		tableModel.addColumn("sedie");
		tableModel.addColumn("sdraio");
		tableModel.addColumn("prezzo");
		tableModel.addColumn("cliente");
		tableModel.addColumn("bagnino");
		
		
		

		for (var o : ombrelloniConPrenotazioni) {
			
				
			
			Object[] rowData = {o.getDataInizio(), o.getDataFine(), o.getnLettini(), o.getnSedie(), o.getnSdraio(),
					o.getPrezzo(), o.getCliente(), o.getBagnino()};
			tableModel.addRow(rowData);
		}
		
		
		MyDefaultTableCellRenderer.resizeAndCenterTable(table);

		
		
		panel.add(new JScrollPane(table));
		add(panel);
		
		
		
		
		
		
		
		
		
//		final var panel = new JPanel(new GridBagLayout());
//		panel.setPreferredSize(new Dimension(800,300));
//		setTitle("Prenotazioni");
//		
//		
//		
//		final var conferma = new JButton("Conferma");
//		final JLabel output = new JLabel();
//		final List<String> affitti = new ArrayList<>();
//		final JList<String> affittiJList;	
//		int rowCount = 0;
//		
//		final String query = "SELECT dataInizio, dataFine FROM OmbrelloniConPrenotazione O JOIN Clienti C "
//				+ "ON (O.codiceFiscaleCliente = C.codiceFiscale) WHERE anno = ? "
//				+ "AND numeroOmbrellone = ? AND NOT ((dataInizio < ? AND dataFine < ?) OR (dataInizio > ? AND dataFine > ?))";
//		try (final PreparedStatement statement = connection.prepareStatement(query)) {
//        	statement.setInt(1, anno);
//        	statement.setInt(2, numeroOmbrellone);
//        	statement.setDate(3, Utils.dateToSqlDate(dataInizio));
//        	statement.setDate(4, Utils.dateToSqlDate(dataInizio));
//        	statement.setDate(5, Utils.dateToSqlDate(dataFine));
//        	statement.setDate(6, Utils.dateToSqlDate(dataFine));
//        	final ResultSet resultSet = statement.executeQuery();
//        	
//        	while(resultSet.next()) {
//        		affitti.add(resultSet.getString("dataInizio") + ", " + resultSet.getString("dataFine"));
//        		rowCount++;
//        	}
//        }
//		if (rowCount == 0) {
//			output.setText("Nessuna prenotazione");
//			conferma.setVisible(false);
//		}
//		affittiJList = new JList(affitti.toArray());
//		
//		conferma.addActionListener(l -> {
//			final var selectAffitto = affittiJList.getSelectedValue();
//			if (selectAffitto != null) {
//				final var values = selectAffitto.split(", ");
//				final var comand = "SELECT O.prezzo, Cl.codiceFiscale, O.codiceUnivocoBagnino, C.codiceTipoSeduta, C.quantita, Cl.nome, "
//						+ "Cl.cognome, Cl.telefono, codiceTipoCliente FROM OmbrelloniConPrenotazione O JOIN Composizioni C JOIN Clienti Cl "
//						+ "ON (O.anno = C.anno AND O.numeroOmbrellone = C.numeroOmbrellone AND O.dataInizio = C.dataInizio "
//						+ "AND O.codiceFiscaleCliente = Cl.codiceFiscale) WHERE O.anno = ? AND O.numeroOmbrellone = ? AND O.dataInizio = ?";
//				try (final PreparedStatement statement = connection.prepareStatement(comand)) {
//		        	statement.setInt(1, anno);
//		        	statement.setInt(2, numeroOmbrellone);
//		        	statement.setDate(3, java.sql.Date.valueOf(values[0]));
//		        	final ResultSet resultSet = statement.executeQuery();
//		        	String descrizione;
//		        	if (resultSet.next()) {
//		        		descrizione = "<html>nOmbrellone: " + numeroOmbrellone + ", anno: " + anno + ", inizio: " + values[0] + ", fine: " + values[1]
//		        				+ ", prezzo: " + resultSet.getString("prezzo") + ", codBagnino: " + resultSet.getInt("codiceUnivocoBagnino") 
//		        				+ "<br>nome: " + resultSet.getString("nome") + ", cognome: " + resultSet.getString("cognome")
//		        				+ "<br> telefono: " + resultSet.getString("telefono") + ", codTipoCliente: "
//		        				+ resultSet.getInt("codiceTipoCliente") + ", CF: " + resultSet.getString("codiceFiscale");
//			        	do {
//			        		descrizione = descrizione + "<br>tipoSeduta: " + resultSet.getInt("codiceTipoSeduta") + ", quantità: " + resultSet.getInt("quantita");
//			        	} while (resultSet.next());
//			        	descrizione = descrizione + "</html>";
//			        	output.setText(descrizione);
//		        	}
//		        } catch (final Exception e) {
//		        	throw new IllegalStateException(e);
//		        }
//			}
//		});
//		
//		panel.add(affittiJList);
//		panel.add(conferma);
//		var c = new GridBagConstraints();
//		c.gridy = 1;
//		c.gridwidth = 3;
//		panel.add(output, c);
//		add(panel);
		
		
		
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
