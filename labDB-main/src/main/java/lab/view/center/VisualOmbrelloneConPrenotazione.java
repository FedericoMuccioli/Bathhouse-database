package lab.view.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import lab.db.Query;
import lab.view.utilities.MyDefaultTableCellRenderer;

public class VisualOmbrelloneConPrenotazione extends JDialog {

	public VisualOmbrelloneConPrenotazione(Query query, int numeroOmbrellone, int anno, Date dataInizio, Date dataFine) {
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
		
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = table.getSelectedRow();
		            if (selectedRow != -1) {
		            	try {
							new AddOrdine(query, anno, numeroOmbrellone, (Date) tableModel.getValueAt(selectedRow, 0));
						} catch (SQLException e1) {
							alert.setText("Errore, riprovare");
						}
		            }
		        }
		    }
		});
		
		
		panel.add(new JScrollPane(table));
		panel.add(alert, BorderLayout.SOUTH);
		add(panel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}


