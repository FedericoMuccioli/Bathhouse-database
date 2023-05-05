package lab.view.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lab.db.Query;
import lab.view.utilities.MyDefaultTableCellRenderer;

public class VisualOmbrelloneConPrenotazione extends JDialog {

	public VisualOmbrelloneConPrenotazione(Query query, final int numeroOmbrellone, final int anno, 
			final Date dataInizio, final Date dataFine) {
		var panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(800,300));
		setTitle("Prenotazioni ombrellone:" + String.valueOf(numeroOmbrellone));
		var tableModel = new DefaultTableModel();
		var table = new JTable(tableModel);
		var alert = new JLabel();

		try {
			var ombrelloniConPrenotazioni = query.getPrenotazioniOmbrellone(anno, numeroOmbrellone, dataInizio, dataFine);
			table.getTableHeader().setDefaultRenderer(new MyDefaultTableCellRenderer());
			tableModel.addColumn("dataInizio");
			tableModel.addColumn("dataFine");
			tableModel.addColumn("lettini");
			tableModel.addColumn("sedie");
			tableModel.addColumn("sdraio");
			tableModel.addColumn("prezzo");
			tableModel.addColumn("cliente");
			tableModel.addColumn("bagnino");

			if (!ombrelloniConPrenotazioni.isEmpty()) {
				for (var o : ombrelloniConPrenotazioni) {
					Object[] rowData = {o.getDataInizio(), o.getDataFine(), o.getnLettini(), o.getnSedie(), o.getnSdraio(), o.getPrezzo(), o.getCliente(), o.getBagnino()};
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
