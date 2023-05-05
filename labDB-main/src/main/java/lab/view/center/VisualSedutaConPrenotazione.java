package lab.view.center;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lab.db.Query;
import lab.view.utilities.MyDefaultTableCellRenderer;

public class VisualSedutaConPrenotazione extends JDialog {
	
	public VisualSedutaConPrenotazione(Query query, final int numeroSeduta, final int anno, 
			final Date dataInizio, final Date dataFine) throws SQLException {
		var panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(800,300));
		setTitle("Prenotazioni seduta:" + String.valueOf(numeroSeduta));
		var tableModel = new DefaultTableModel();
		var table = new JTable(tableModel);
		var alert = new JLabel();
		
		try {
			var prenotazioniSeduta = query.getPrenotazioniSeduta(anno, numeroSeduta, dataInizio, dataFine);
			table.getTableHeader().setDefaultRenderer(new MyDefaultTableCellRenderer());
			tableModel.addColumn("dataInizio");
			tableModel.addColumn("dataFine");
			tableModel.addColumn("tipoSeduta");
			tableModel.addColumn("prezzo");
			tableModel.addColumn("cliente");
			tableModel.addColumn("bagnino");

			if (!prenotazioniSeduta.isEmpty()) {
				for (var o : prenotazioniSeduta) {
					Object[] rowData = {o.getDataInizio(), o.getDataFine(), o.getTipoSeduta(), o.getPrezzo(), o.getCliente(), o.getBagnino()};
					tableModel.addRow(rowData);
				}
				MyDefaultTableCellRenderer.resizeAndCenterTable(table);
			}

		} catch (Exception e) {
			alert.setText("Riprovare, errore nell'esecuzione della query");
		}
		
		panel.add(new JScrollPane(table));
		panel.add(alert, BorderLayout.SOUTH);
		add(panel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
